package online.superarilo.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import online.superarilo.myblog.annotation.Log;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.service.impl.MediaManagerServiceImpl;
import online.superarilo.myblog.utils.*;
import online.superarilo.myblog.vo.ImageRelativeAbsolutePathVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/user")
public class UserInformationController {

    private IUserInformationService userInformationService;

    @Autowired
    private Environment environment;

    @Autowired
    public void setUserInformationService(IUserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @Log
    @GetMapping("/information")
    public Result<Map<String, Object>> queryUserInfo(HttpServletRequest request) {
        return userInformationService.queryUserInfo(request);
    }

    /**
     * 用户修改个人信息
     */
    @Log
    @PostMapping("/{uid}")
    public Result<String> updateUserInfo(@PathVariable("uid") Long uid, @RequestBody UserInformation user,
                                         HttpServletRequest request) {
        if (Objects.isNull(user.getUid())) {
            user.setUid(uid);
        }
        return userInformationService.updateUserInfo(user, request);
    }

    /***
     * 上传头像
     *
     * @param headerFile
     *            文件
     * @return 路径
     */
    @Log
    @PostMapping("/header/upload")
    public JsonResult uploadHeader(@RequestParam("headerFile") MultipartFile headerFile,
                                                            HttpServletRequest request) {
        if (headerFile != null && !headerFile.isEmpty()) {
            String token = request.getHeader("token");
            UserInformation user = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
            if (user == null) {
                return JsonResult.ERROR(HttpStatus.UNAUTHORIZED.value(), "登录失效，请重新登录");
            }
            // 检查文件类型
            List<MultipartFile> multipartFiles = new ArrayList<>();
            multipartFiles.add(headerFile);
            JsonResult jsonResult = MediaManagerServiceImpl.checkFile(multipartFiles, FileMultipartUtil.IMAGE_FILE_SUFFIX);
            if(!Objects.isNull(jsonResult)) {
                return jsonResult;
            }


            // 文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "");
            String originalFilename = headerFile.getOriginalFilename();
            if(StringUtils.hasLength(originalFilename)) {
                fileName += originalFilename.substring(originalFilename.lastIndexOf("."));
            }else {
                return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "文件不能为空");
            }
            // 相对路径
            String relativePosition;
            try {
                relativePosition = UploadFileSevenNiuYunUtil.upload(headerFile.getInputStream(), fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return JsonResult.ERROR(500, "上传失败");
            }
            // 拼接全路径
            String absolutePosition = environment.getProperty("seven_niu_yun.httpUrl") + relativePosition;

            UserInformation userInformation = new UserInformation();
            userInformation.setUid(user.getUid());
            userInformation.setUserhead(absolutePosition);
            if (userInformationService.updateById(userInformation)) {
                return JsonResult.OK("上传成功");
            }
        }
        return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "上传失败", null);
    }

    /**
     * Minecraft ID验证
     *
     * @param javaMcId 录入ID
     * @return Result<?>
     */
    @Log
    @PostMapping("/whitelist")
    public JsonResult whitelist(@RequestParam String javaMcId, HttpServletRequest request) {

        String token = request.getHeader("token");
        UserInformation user = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);

        if (javaMcId == null) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "参数错误");
        } else if (javaMcId.length() < 1) {
            return JsonResult.OK("请输入要绑定的ID");
        }

        return userInformationService.whitelist(javaMcId, user);
    }

    @Log
    @PostMapping("/updateWhitelist")
    public JsonResult updateWhitelist(HttpServletRequest request) {

        String token = request.getHeader("token");
        UserInformation user = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);

        return userInformationService.updateWhitelist(user);

    }

}
