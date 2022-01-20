package online.superarilo.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.MediaManager;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.MediaManagerMapper;
import online.superarilo.myblog.service.IMediaManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.FTPUtil;
import online.superarilo.myblog.utils.FileMultipartUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.ImageRelativeAbsolutePathVO;
import online.superarilo.myblog.vo.MediaManagerVO;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Result<List<MediaManager>> listMediaByUid(Integer uid) {

        UserInformation selUser = userInformationService.getById(uid);
        if(selUser == null) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "未找到该用户", null);
        }
        List<MediaManager> data = this.list(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid));
        return new Result<>(true, HttpStatus.OK, "查询成功", data);
    }

    @Override
    public Result<List> uploadImages(List<MultipartFile> files, Integer uid) {
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
        Result<List> checkFilesResult = this.checkFile(files, FileMultipartUtil.IMAGE_FILE_SUFFIX);
        if(checkFilesResult != null) {
            return checkFilesResult;
        }

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
    public Result<String> removeMediaByMeidaIdAndUid(List<Integer> mediaIds, Integer uid) {
        UserInformation selUser = userInformationService.getById(uid);
        if(selUser == null) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "当前用户不存在","当前用户不存在");
        }
        if(mediaIds == null || mediaIds.isEmpty()) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "选择要删除的资源","选择要删除的资源");
        }
        List<MediaManager> list = this.list(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid).in(MediaManager::getId, mediaIds));
        // 用户删除的资源集合
        List<MediaManager> removeMediaRelativePaths = new ArrayList<>();
        if(list == null || list.size() == 0) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "删除的资源不存在或已删除","删除的资源不存在或已删除");
        }
        list.forEach((item) -> {
            if(mediaIds.contains(item.getId())) {
                removeMediaRelativePaths.add(item);
            }
        });

        this.remove(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getUid, uid).in(MediaManager::getId, mediaIds));

        // 异步删除图片服务器资源
        if(removeMediaRelativePaths.size() > 0) {
            new Thread(() -> {
                FTPClient ftpClient = FTPUtil.getFTPClient();
                removeMediaRelativePaths.forEach((item) -> {
                    String parentFolder = item.getMediaUrl().substring(0, item.getMediaUrl().lastIndexOf("/"));
                    String fileName = item.getMediaName();
                    FTPUtil.deleteFileByFileName(ftpClient, parentFolder, fileName);
                });
                FTPUtil.closeFTP(ftpClient);
            }).start();
        }

        return new Result<>(true, HttpStatus.OK, "success", "删除成功");
    }

    /**
     * 检查文件
     * @param files
     * @return
     */
    private Result<List> checkFile(List<MultipartFile> files, List<String> suffixes) {
        List<String> errorList = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if(!suffixes.contains(suffix.toUpperCase())) {
                errorList.add(originalFilename + " 图片格式不正确，目前支持 JPG, PNG, JPEG, GIF格式");
            }
        }
        return errorList.isEmpty() ? null : new Result<>(false, HttpStatus.BAD_REQUEST, "error", errorList);
    }

}
