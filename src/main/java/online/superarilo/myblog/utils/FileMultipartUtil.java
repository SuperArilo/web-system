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

import java.io.*;
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
    public static final List<String> IMAGE_FILE_SUFFIX = Arrays.asList("JPG", "PNG", "JPEG", "GIF");

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
     * @throws RuntimeException
     * @return 返回图片在本地的绝对路径集合
     */
    public static List<ByteArrayOutputStream> compressFile(List<MultipartFile> files) throws RuntimeException {
        // 压缩因子
        float compressFactor = 0.7f;

        // 压缩后格式为 jpg
        String compressFormat = "jpg";

        // 创建父文件夹
//        File outputFileParentAdressFile = new File(localBasePath);
//        if(!outputFileParentAdressFile.exists()) {
//            outputFileParentAdressFile.mkdirs();
//        }

        // 文件集合绝对路径
//        String[] filePath = new String[files.size()];

        // 压缩后输出流类集合
        List<ByteArrayOutputStream> fs = new ArrayList<>();

        // 创建文件类并添加到压缩后输出本地的文件集合中
        for (int i = 0; i < files.size(); i++) {
//            filePath[i] = localBasePath + UUID.randomUUID().toString().replaceAll("-", "") + "." + compressFormat;
            fs.add(new ByteArrayOutputStream());
        }

        // 开始压缩
        InputStream[] inputStreams = null;
        try {
            inputStreams = createInputStreams(files);
            Thumbnails.of(inputStreams).outputQuality(compressFactor).outputFormat(compressFormat).scale(1).toOutputStreams(fs);
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
        return fs;
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
     * 上传头像
     */
    public static ImageRelativeAbsolutePathVO uploadHeader(MultipartFile headerFile) {
        String ftpPath = getImageServerBasePath("images/header");
        // 图片名称
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais  = null;
        InputStream is = null;

        try {
            is = headerFile.getInputStream();
            Thumbnails.of(is).outputFormat("png")/*.size(128, 128)*/.scale(1).toOutputStream(baos);
            bais = new ByteArrayInputStream(baos.toByteArray());
            // 上传图片
            FTPClient ftpClient = FTPUtil.getFTPClient();

            boolean b = FTPUtil.uploadFile(ftpClient, ftpPath, fileName, bais);
            if(!b) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hanldeImageRelativeHttpRequest(ftpPath, fileName);
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
        imageRelativeAbsolutePathVO.setFileName(fileName);
        imageRelativeAbsolutePathVO.setRelativePath(relativePath);
        imageRelativeAbsolutePathVO.setAbsolutePath(environment.getProperty("pictureHttp") + relativePath);

        return imageRelativeAbsolutePathVO;
    }
}
