package online.superarilo.myblog.utils;

import net.coobird.thumbnailator.Thumbnails;
import online.superarilo.myblog.vo.ImageRelativeAbsolutePathVO;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件工具类
 */
@Component
public class FileMultipartUtil {

    private static Logger logger = LogManager.getLogger(FileMultipartUtil.class);

    /**
     * 图片扩展名称
     */
    public static final List<String> IMAGE_FILE_SUFFIX = Arrays.asList("JPG", "PNG", "JPEG");

    private static Environment environment;
    @Autowired
    public void setEnvironment(Environment env) {
        FileMultipartUtil.environment = env;
    }

    /**
     * 通过key查询路径
     * @param key
     * @return
     */
    private static String getBasePath(String key) {
        return environment.getProperty(key);
    }

    /**
     * 获取图片压缩父路径
     * @return
     */
    public static String systemRoot() {
        String operatingSystemName = System.getProperty("os.name");
        String root = "";
        if(operatingSystemName != null && operatingSystemName.toLowerCase().startsWith("windows")) {
            root = getBasePath("uploadFileAddress.windows_base_path");
        } else {
            root = getBasePath("uploadFileAddress.linux_base_path");
        }
        return root;
    }

    /**
     * 获取图片服务器父路径, 根据business拼接父路径
     * @param business
     * @return
     */
    public static String getImageServerBasePath(String business) {
        return getBasePath("uploadFileAddress.picture_server_base_path") + business + new SimpleDateFormat("/" + DateUtils.YYYY_MM_DD_BIAS_PATTERN + "/").format(new Date());
    }



    /**
     * 压缩图片保存到本地
     * @param files
     * @param localBasePath
     * @throws RuntimeException
     * @return 返回图片在本地的绝对路径集合
     */
    public static String[] compressFile(List<MultipartFile> files, String localBasePath) throws RuntimeException {
        // 压缩因子
        float compressFactor = 0.7f;

        // 压缩后格式为 jpg
        String compressFormat = "jpg";

        // 创建父文件夹
        File outputFileParentAdressFile = new File(localBasePath);
        if(!outputFileParentAdressFile.exists()) {
            outputFileParentAdressFile.mkdirs();
        }

        // 文件集合绝对路径
        String[] filePath = new String[files.size()];

        // 压缩后输出本地的文件类集合
        List<File> fs = new ArrayList<>();

        // 创建文件类并添加到压缩后输出本地的文件集合中
        for (int i = 0; i < files.size(); i++) {
            filePath[i] = localBasePath + UUID.randomUUID().toString().replaceAll("-", "") + "." + compressFormat;
            fs.add(new File(filePath[i]));
        }

        // 开始压缩
        InputStream[] inputStreams = null;
        try {
            inputStreams = createInputStreams(files);
            Thumbnails.of(inputStreams).outputQuality(compressFactor).outputFormat(compressFormat).scale(1).toFiles(fs);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("压缩图片失败");
        }finally {
            try {
                closeInputStreams(inputStreams);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("关闭流失败");

            }
        }
        return filePath;
    }

    /**
     *获取流对象数组
     * @param files
     * @return
     * @throws IOException
     */
    private static InputStream[] createInputStreams(List<MultipartFile> files) throws IOException {
        InputStream[] inputStreams = new InputStream[files.size()];
        for (int i = 0; i < files.size(); i++) {
            inputStreams[i] = files.get(i).getInputStream();
        }
        return inputStreams;
    }

    /**
     * 关闭流
     * @param inputStreams
     */
    private static void closeInputStreams(InputStream[] inputStreams) throws IOException {
        if(inputStreams != null && inputStreams.length > 0) {
            for (InputStream i : inputStreams) {
                if(i != null) {
                    i.close();
                }
            }
        }
    }

    /**
     * 处理相对路径和图片请求地址
     * @param ftpPath
     * @param fileName
     * @return
     */
    public static ImageRelativeAbsolutePathVO hanldeImageRelativeHttpRequest(String  ftpPath, String fileName) {
        String relativeBasePath = ftpPath.replaceFirst(getBasePath("uploadFileAddress.picture_server_base_path"), "");
        ImageRelativeAbsolutePathVO imageRelativeAbsolutePathVO = new ImageRelativeAbsolutePathVO();
        String relativePath = relativeBasePath + fileName;
        imageRelativeAbsolutePathVO.setRelativePath(relativePath);
        imageRelativeAbsolutePathVO.setAbsolutePath(environment.getProperty("pictureHttp") + relativePath);
        return imageRelativeAbsolutePathVO;
    }
}
