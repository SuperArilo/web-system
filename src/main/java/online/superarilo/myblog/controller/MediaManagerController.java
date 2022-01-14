package online.superarilo.myblog.controller;


import online.superarilo.myblog.service.IMediaManagerService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     * 用户上传图片
     * @param imageFiles
     * @param uid
     * @return
     */
    @PostMapping("/upload/image")
    public Result<List> uploadImages(@RequestParam("imageFiles") List<MultipartFile> imageFiles, @RequestParam("uid") Integer uid) {
        return mediaManagerService.uploadImages(imageFiles, uid);
    }
}
