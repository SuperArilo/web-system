package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.MediaManager;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.MediaManagerMapper;
import online.superarilo.myblog.service.IMediaManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.*;
import online.superarilo.myblog.vo.ImageRelativeAbsolutePathVO;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-12
 */
@Service
public class MediaManagerServiceImpl extends ServiceImpl<MediaManagerMapper, MediaManager> implements IMediaManagerService {

    @Autowired
    private IUserInformationService userInformationService;

    @Autowired
    private Environment environment;


    @Override
    public Result<List<MediaManager>> listMediaByUid(Long uid) {

        UserInformation selUser = userInformationService.getById(uid);
        if(selUser == null) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "未找到该用户", null);
        }
        List<MediaManager> data = this.list(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid));
        return new Result<>(true, HttpStatus.OK, "查询成功", data);
    }

    @Override
    public Result<List> uploadImages(List<MultipartFile> files, Long uid) {
        if(uid == null || userInformationService.getById(uid) == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "用户不存在");
        }
        List<MediaManager> userMediaList = this.list(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid));
        if(userMediaList.size() >= 16) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "最多保存十六张图片");
        }
        if(files == null || files.isEmpty()) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "请选择图片文件");
        }
        if(files.size() > 4) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "单次最多上传4张图片");
        }
        // 检查图片
//        Result<List> checkFilesResult = this.checkFile(files, FileMultipartUtil.IMAGE_FILE_SUFFIX);
//        if(checkFilesResult != null) {
//            return checkFilesResult;
//        }

//        // 获取本地存储路径
//        String localBasePath = FileMultipartUtil.systemRoot();
//
        // 获取图片服务器图片存储父路径
        String business = "images";
        String imageServerBasePath = FileMultipartUtil.getImageServerBasePath(business);

        // 格式为  jpg/png/jpeg 进行压缩
        List<MultipartFile> tempFiles = new ArrayList<>();
        List<InputStream> gifFiles = new ArrayList<>();
        files.forEach((item) -> {
            if(!Objects.equals("GIF", item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf(".") + 1).toUpperCase())) {
                tempFiles.add(item);
            }else {
                try {
                    gifFiles.add(item.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // MultipartFile对象压缩成图片保存到本地，返回图片绝对路径
        List<ByteArrayOutputStream> byteArrayOutputStreams = null;
        if(tempFiles.size() > 0) {
            byteArrayOutputStreams = FileMultipartUtil.compressFile(tempFiles);
        }

        // 返回数据
        List<ImageRelativeAbsolutePathVO> list = new ArrayList<>();

        // 将本地图片上传至图片服务器
        FTPClient ftpClient = FTPUtil.getFTPClient();

        // 使用集合的foreach方法
        if(Objects.nonNull(byteArrayOutputStreams) && byteArrayOutputStreams.size() > 0) {
            byteArrayOutputStreams.forEach((item) -> {
                // uuid生成图片名称
                String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
                FTPUtil.uploadFile(ftpClient, imageServerBasePath, imageName, new ByteArrayInputStream(item.toByteArray()));
                // 处理相对路径和图片请求地址
                list.add(FileMultipartUtil.hanldeImageRelativeHttpRequest(imageServerBasePath, imageName));
            });
        }

        // 上传gif
        if(gifFiles.size() > 0) {
            for (InputStream gifFile : gifFiles) {
                String imageName = UUID.randomUUID().toString().replaceAll("-", "") + ".gif";
                FTPUtil.uploadFile(ftpClient, imageServerBasePath, imageName, gifFile);
                // 处理相对路径和图片请求地址
                list.add(FileMultipartUtil.hanldeImageRelativeHttpRequest(imageServerBasePath, imageName));
            }

        }
        FTPUtil.closeFTP(ftpClient);

        // 保存到数据库
        List<MediaManager> mediaManagerList = new ArrayList<>();
        list.forEach((item) -> {
            MediaManager mediaManager = new MediaManager();
            mediaManager.setUid(uid);
            mediaManager.setMediaName(item.getFileName());
            mediaManager.setMediaUrl(item.getRelativePath());
            mediaManager.setMediaHttpUrl(item.getAbsolutePath());
            mediaManager.setCreateTime(new Date());
            mediaManagerList.add(mediaManager);
        });
        this.saveBatch(mediaManagerList);

//        // 异步线程删除保存在本地的图片
//        new Thread( () -> {
//            imagesAbsolutePathList.forEach((item) -> {
//                File file = new File(item);
//                if(file.exists()) {
//                    file.delete();
//                }
//            });
//        }).start();

        return new Result<List>(true, HttpStatus.OK, "success", mediaManagerList);
    }

    @Override
    public JsonResult uploadImages(List<MultipartFile> files, HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token) || Objects.isNull(RedisUtil.get(token))) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "登录失效，请重新登录");
        }
        UserInformation user = JSON.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        Long uid = user.getUid();

        List<MediaManager> userMediaList = this.list(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid));
        if(userMediaList.size() >= 16) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "最多保存十六张图片");
        }
        if(files == null || files.isEmpty()) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "请选择图片文件");
        }
        if(files.size() > 4) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "单次最多上传4张图片");
        }
//        检查图片
        JsonResult checkFilesResult = this.checkFile(files, FileMultipartUtil.IMAGE_FILE_SUFFIX);
        if(checkFilesResult != null) {
            return checkFilesResult;
        }

        List<MediaManager> list = new ArrayList<>();
        int count = 0;
        for (MultipartFile item : files) {
            MediaManager mediaManager = new MediaManager();
            String relativePosition = null;
            String key = UUID.randomUUID().toString().replaceAll("-", "");
            String originalFilename = item.getOriginalFilename();
            if(StringUtils.hasLength(originalFilename)) {
                key += originalFilename.substring(originalFilename.lastIndexOf("."));
            } else {
                break;
            }
            try {
                relativePosition = UploadFileSevenNiuYunUtil.upload(item.getInputStream(), key);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(StringUtils.hasLength(relativePosition)) {
                mediaManager.setUid(uid);
                mediaManager.setMediaName(item.getOriginalFilename());
                mediaManager.setMediaUrl(relativePosition);
                mediaManager.setMediaHttpUrl(environment.getProperty("seven_niu_yun.httpUrl") + relativePosition);
                mediaManager.setCreateTime(new Date());
                list.add(mediaManager);
                count++;
            }
        }
        if(count == files.size()) {
            if (this.saveBatch(list)) {
                return JsonResult.OK(list);
            }
        }
        //上传失败一个，全部取消
        if(!list.isEmpty()) {
            list.forEach(item -> {
                UploadFileSevenNiuYunUtil.delete(item.getMediaUrl());
            });
        }
        return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "上传失败");
    }

    @Override
    public JsonResult removeMediaByMediaIdAndUid(List<Long> mediaIds, HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token) || Objects.isNull(RedisUtil.get(token))) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "登录失效，请重新登录");
        }
        UserInformation user = JSON.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        Long uid = user.getUid();
        if(mediaIds == null || mediaIds.isEmpty()) {
            return JsonResult.ERROR(HttpStatus.NOT_FOUND.value(), "选择要删除的资源");
        }
        List<MediaManager> list = this.list(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid).in(MediaManager::getId, mediaIds));
//        // 用户删除的资源集合
//        List<MediaManager> removeMediaRelativePaths = new ArrayList<>();
        if(list == null || list.size() == 0) {
            return JsonResult.ERROR(HttpStatus.NOT_FOUND.value(), "删除的资源不存在或已删除");
        }
//        list.forEach((item) -> {
//            if(mediaIds.contains(item.getId())) {
//                removeMediaRelativePaths.add(item);
//            }
//        });
        for (MediaManager mediaManager : list) {
            UploadFileSevenNiuYunUtil.delete(mediaManager.getMediaUrl());
        }
        this.remove(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid).in(MediaManager::getId, mediaIds));

        // 异步删除图片服务器资源
//        if(removeMediaRelativePaths.size() > 0) {
//            new Thread(() -> {
//                FTPClient ftpClient = FTPUtil.getFTPClient();
//                removeMediaRelativePaths.forEach((item) -> {
//                    String parentFolder = item.getMediaUrl().substring(0, item.getMediaUrl().lastIndexOf("/"));
//                    String fileName = item.getMediaName();
//                    FTPUtil.deleteFileByFileName(ftpClient, parentFolder, fileName);
//                });
//                FTPUtil.closeFTP(ftpClient);
//            }).start();
//        }

        return JsonResult.OK("删除成功");
    }

    /**
     * 检查文件
     * @param files
     * @return
     */
    private JsonResult checkFile(List<MultipartFile> files, List<String> suffixes) {
        List<String> errorList = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if(!suffixes.contains(suffix.toUpperCase())) {
                errorList.add(originalFilename + " 图片格式不正确，目前支持 JPG, PNG, JPEG, GIF格式");
            }
        }
        return errorList.isEmpty() ? null : JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "格式不正确" ,errorList);
    }

}
