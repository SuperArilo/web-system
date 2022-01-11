package online.superarilo.myblog.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件工具类
 */
@Component
public class FileMultipartUtil {

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
     * 压缩图片进行存储
     * @param file
     * @param business
     * @throws RuntimeException
     */
    public static String compressFile(MultipartFile file, String business) throws RuntimeException {
        // 获取当前操作系统类型
        String operatingSystemName = System.getProperty("os.name");
        String root = "";
        if(operatingSystemName != null && operatingSystemName.toLowerCase().startsWith("windows")) {
            root = environment.getProperty("uploadFile.windows_file_server_root");
        }else if(operatingSystemName != null && operatingSystemName.toLowerCase().startsWith("linux")) {
            root = environment.getProperty("uploadFile.linux_file_server_root");
        }else {
            throw new RuntimeException("当前操作系统不支持");
        }

        float compressFactor = 0.7f; // 压缩因子

        // 获取父路径
        String outputFileParentAdress = root + business + "/";
        // 压缩后格式为 jpg
        String compressFormat = "jpg";
        // 文件名
        String fileName = UUID.randomUUID().toString() + "." + compressFormat;
        // 相对路径
        String relativePath = business + "/" + fileName;

        File outputFileParentAdressFile = new File(outputFileParentAdress);
        if(!outputFileParentAdressFile.exists()) {
            outputFileParentAdressFile.mkdirs();
        }

        String filePath = outputFileParentAdress + fileName;
        File path = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Thumbnails.of(inputStream).outputQuality(compressFactor).outputFormat(compressFormat).scale(1).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativePath;
    }
}
