package online.superarilo.myblog.service.impl;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import online.superarilo.myblog.entity.MediaManager;
import online.superarilo.myblog.service.IFileMultipartService;
import online.superarilo.myblog.service.IMediaManagerService;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.FileMultipartUtil;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class FileMultipartServiceImpl implements IFileMultipartService {

    @Autowired
    private Environment env;

    @Autowired
    private IUserInformationService userInformationService;

    @Autowired
    private IMediaManagerService mediaManagerService;


//    @Override
//    public Result<Map<String, Object>> uploadImage(List<MultipartFile> imageFiles, Integer uid) {
//        if(uid == null || userInformationService.getById(uid) == null) {
//            return new Result<>(false, HttpStatus.BAD_REQUEST, "用户不存在");
//        }
//        if(imageFiles == null || imageFiles.isEmpty()) {
//            return new Result<>(false, HttpStatus.BAD_REQUEST, "图片文件不能为空");
//        }
//        // 检查是否为图片格式
//        Result<Map<String, Object>> suffixesError = this.checkFileSuffix(imageFiles, FileMultipartUtil.IMAGE_FILE_SUFFIX);
//        if(suffixesError != null) {
//            return suffixesError;
//        }
//        String path = FileMultipartUtil.compressFile(imageFile, "image");
//
//        // todo 区分用户 将连接存放数据库
//        MediaManager mediaManager = new MediaManager();
//        mediaManager.setUid(uid);
//        mediaManager.setCreateTime(new Date());
//        mediaManager.setMediaUrl(path);
//        mediaManagerService.save(mediaManager);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("relativePath", path);
//        String key = System.getProperty("os.name").toLowerCase().startsWith("windows") ? "uploadFile.windows_file_server_url" : "uploadFile.linux_file_server_url";
//        map.put("absolutePath", env.getProperty(key) + path);
//        return new Result<>(true, HttpStatus.OK, "上传成功", map);
//    }

    /**
     *
     * @return
     */
    private Result<Map<String, Object>> checkFileSuffix(List<MultipartFile> files, List<String> suffixes) {
        List<Map<String, String>> errorList = new ArrayList<>();
        for (MultipartFile file : files) {
            Map<String, String> errorMap = new HashMap<>();
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if(!FileMultipartUtil.IMAGE_FILE_SUFFIX.contains(suffix.toUpperCase())) {
                errorMap.put("originalFilename", "图片格式不正确，目前支持 JPG, PNG, JPEG格式");
            }
            errorList.add(errorMap);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("error", errorList);
        return new Result<>(false, HttpStatus.BAD_REQUEST, "error", result);
    }
}
