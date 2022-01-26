package online.superarilo.myblog.controller;


import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/verification")
public class VerificationController {


	private final Color[] colors = {
			Color.RED,
			Color.BLUE,
			Color.CYAN,
			Color.PINK,
			Color.GRAY,
			Color.GREEN,
			Color.BLACK,
			Color.WHITE,
			Color.ORANGE,
			Color.YELLOW,
			Color.MAGENTA,
			Color.DARK_GRAY,
			Color.LIGHT_GRAY
	};

	private static final java.util.List<String> VERIFY_METHODS = Arrays.asList("login", "register");



	@GetMapping("/image/{method}")
	public Result verificationForImage(@PathVariable("method") String method, String random, HttpServletResponse response) {
		// 验证获取验证码是否是可行方式
		if(!VERIFY_METHODS.contains(method) || !StringUtils.hasLength(random)) {
			return new Result(false, HttpStatus.BAD_REQUEST, "获取验证码错误");
		}

		int imageWidth = 500;
		int imageHeight = 100;

		int verificationCodeLength = 4;

		BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		Color defaultColor = graphics.getColor();

		// 生成随机验证码并放入redis
		char[] chars = UUID.randomUUID().toString().substring(0, verificationCodeLength).toCharArray();
		String key = method.toLowerCase() + random + RedisUtil.VERIFY_CODE_REDIS_KEY_SUFFIX;
		StringBuilder sb = new StringBuilder();
		for (char aChar : chars) {
			sb.append(aChar);
		}
		RedisUtil.set(key, sb.toString(), 3 * 60, TimeUnit.SECONDS);


		for (Color color : colors) {
			graphics.setColor(color);
			int max = 60000 / colors.length;
			for (int j = 0; j < max; j++) {
				int x = Double.valueOf(Math.random() * imageWidth + 1).intValue();
				int y = Double.valueOf(Math.random() * imageHeight + 1).intValue();
				graphics.drawOval(x, y, 1, 1);
			}
		}

		String fontStyle = "楷体";
		int fontSize = 100;
		graphics.setFont(new Font(fontStyle, Font.ITALIC, fontSize));

		for (int i = 0; i < chars.length; i++) {
			// 随机选取一种颜色
			graphics.setColor(colors[Double.valueOf(Math.random() * colors.length).intValue()]);
			// 横向
			graphics.drawLine(0, Double.valueOf(Math.random() * imageHeight + 1).intValue(), imageWidth, Double.valueOf(Math.random() * imageHeight + 1).intValue());
			// 纵向
			graphics.drawLine(Double.valueOf(Math.random() * imageWidth + 1).intValue(), 0, Double.valueOf(Math.random() * imageWidth + 1).intValue(), imageHeight);
			int crosswiseOffset = 5;
			int lengthwaysOffset = 80;
			graphics.drawString(String.valueOf(chars[i]), i * (imageWidth / chars.length) + crosswiseOffset, lengthwaysOffset);
		}

		graphics.setColor(defaultColor);
		graphics.dispose();

		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try {

			ImageIO.write(bufferedImage, "jpg", baos = new ByteArrayOutputStream());
			bais = new ByteArrayInputStream(baos.toByteArray());
			byte[] data = new byte[baos.size()];
			bais.read(data, 0, data.length);
			baos.flush();
			return new Result(true, HttpStatus.OK, "获取验证码成功", data);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false, HttpStatus.INTERNAL_SERVER_ERROR, "获取失败");
		}finally {
			if(bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
