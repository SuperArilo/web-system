package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.MediaManager;
import online.superarilo.myblog.service.IMediaManagerService;
import online.superarilo.myblog.utils.Result;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/media/manager")
public class MediaManagerController {

    @Autowired
    private IMediaManagerService mediaManagerService;

    /**
     * 获取用户的媒体管理器数据
     * @param uid
     * @return
     */
    @GetMapping("/list")
    public Result<List<MediaManager>> listMediaByUid(@RequestParam("uid") Integer uid) {
        return mediaManagerService.listMediaByUid(uid);
    }

    /**
     * 用户上传图片
     * @param imageFiles
     * @param uid
     * @return
     */
    @PostMapping("/upload/image")
    public Result<List> uploadImages(@RequestParam("imageFiles") List<MultipartFile> imageFiles, @RequestParam("uid") Integer uid) {
        return mediaManagerService.uploadImages(imageFiles, uid);
    }

    /**
     * 删除用户的媒体资源
     * @param mediaIds
     * @param uid
     * @return
     */
    @PostMapping("/remove")
    public Result<String> removeMediaByMeidaIdAndUid(@RequestParam("mediaIds") List<Integer>  mediaIds, Integer uid) {
        return mediaManagerService.removeMediaByMeidaIdAndUid(mediaIds, uid);
    }
}
