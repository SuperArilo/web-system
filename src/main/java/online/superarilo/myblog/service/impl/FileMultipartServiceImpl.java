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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileMultipartServiceImpl implements IFileMultipartService {

    @Autowired
    private Environment env;

    @Autowired
    private IUserInformationService userInformationService;

    @Autowired
    private IMediaManagerService mediaManagerService;


    @Override
    public Result<Map<String, String>> uploadImage(MultipartFile imageFile, Integer uid) {
        if(uid == null || userInformationService.getById(uid) == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "用户不存在");
        }
        if(imageFile == null || imageFile.isEmpty()) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "图片文件不能为空");
        }
        String originalFilename = imageFile.getOriginalFilename();
        if(originalFilename == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "图片名称不能为空");
        }
        // 检查是否为图片格式
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if(!FileMultipartUtil.IMAGE_FILE_SUFFIX.contains(suffix.toUpperCase())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "图片格式不正确，目前支持 JPG, PNG, JPEG格式");
        }

        String path = FileMultipartUtil.compressFile(imageFile, "image");

        // todo 区分用户 将连接存放数据库
        MediaManager mediaManager = new MediaManager();
        mediaManager.setUid(uid);
        mediaManager.setCreateTime(new Date());
        mediaManager.setMediaUrl(path);
        mediaManagerService.save(mediaManager);

        Map<String, String> map = new HashMap<>();
        map.put("relativePath", path);
        String key = System.getProperty("os.name").toLowerCase().startsWith("windows") ? "uploadFile.windows_file_server_url" : "uploadFile.linux_file_server_url";
        map.put("absolutePath", env.getProperty(key) + path);
        return new Result<>(true, HttpStatus.OK, "上传成功", map);
    }
}
