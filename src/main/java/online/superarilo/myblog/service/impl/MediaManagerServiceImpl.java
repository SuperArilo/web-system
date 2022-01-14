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

import java.io.File;
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
        if(files == null || files.isEmpty()) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "请选择图片文件");
        }
        // 检查图片
        Result<List> checkFilesResult = this.checkFile(files, FileMultipartUtil.IMAGE_FILE_SUFFIX);
        if(checkFilesResult != null) {
            return checkFilesResult;
        }

        // 获取本地存储路径
        String localBasePath = FileMultipartUtil.systemRoot();

        // 获取图片服务器图片存储父路径
        String business = "images";
        String imageServerBasePath = FileMultipartUtil.getImageServerBasePath(business);

        // MultipartFile对象压缩成图片保存到本地，返回图片绝对路径
        String[] imagesAbsolutePath = FileMultipartUtil.compressFile(files, localBasePath);

        // 返回数据
        List<ImageRelativeAbsolutePathVO> list = new ArrayList<>();
        // 将本地图片上传至图片服务器
        FTPClient ftpClient = FTPUtil.getFTPClient(environment.getProperty("pictureServer.host"),
                Integer.parseInt(environment.getProperty("pictureServer.port")),
                environment.getProperty("pictureServer.user"),
                environment.getProperty("pictureServer.password"));

        // 使用集合的foreach方法
        List<String> imagesAbsolutePathList = Arrays.asList(imagesAbsolutePath);
        imagesAbsolutePathList.forEach((item) -> {
            // 最后一个 / 所在位置
            int index = item.lastIndexOf("/") + 1;
            String basePath = item.substring(0, index);
            String fileName = item.substring(index);
            FTPUtil.uploadFile(ftpClient, basePath, imageServerBasePath, fileName);
            // 处理相对路径和图片请求地址
            list.add(FileMultipartUtil.hanldeImageRelativeHttpRequest(imageServerBasePath, fileName));
        });
        /*for (String s : imagesAbsolutePath) {
            // 最后一个 / 所在位置
            int index = s.lastIndexOf("/") + 1;
            String basePath = s.substring(0, index);
            String fileName = s.substring(index);
            FTPUtil.uploadFile(ftpClient, basePath, imageServerBasePath, fileName);
            // 处理相对路径和图片请求地址
            list.add(FileMultipartUtil.hanldeImageRelativeHttpRequest(imageServerBasePath, fileName));
        }*/
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

        // 异步线程删除保存在本地的图片
        new Thread( () -> {
            imagesAbsolutePathList.forEach((item) -> {
                File file = new File(item);
                if(file.exists()) {
                    file.delete();
                }
            });
            /*for (String path : imagesAbsolutePath) {
                File file = new File(path);
                if(file.exists()) {
                    String parent = file.getParent();
                    File file1 = new File(parent);
                    if(file1.exists()) {
                        file1.delete();
                    }
                }
            }*/
        }).start();



        return new Result<List>(true, HttpStatus.OK, "success", mediaManagerList);
    }

    @Override
    public Result<String> removeMediaByMeidaIdAndUid(Integer mediaId, Integer uid) {
        MediaManager selMediaManager = this.getById(mediaId);
        if (selMediaManager == null) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "删除用的资源不存在","删除用的资源不存在");
        }
        UserInformation selUser = userInformationService.getById(uid);
        if(selUser == null) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "当前用户不存在","当前用户不存在");
        }
        this.remove(new QueryWrapper<MediaManager>().lambda().eq(MediaManager::getId, mediaId).eq(MediaManager::getUid, uid));
        // TODO 删除图片服务器资源
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
                errorList.add(originalFilename + " 图片格式不正确，目前支持 JPG, PNG, JPEG格式");
            }
        }
        return errorList.isEmpty() ? null : new Result<>(false, HttpStatus.BAD_REQUEST, "error", errorList);
    }

}
