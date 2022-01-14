package online.superarilo.myblog.controller;

import online.superarilo.myblog.service.IFileMultipartService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileMultipartController {

    @Autowired
    private IFileMultipartService fileMultipartService;

//    @PostMapping("/upload/image")
//    public Result<Map<String, String>> uploadImage(@RequestParam("imageFiles") List<MultipartFile> imageFiles, @RequestParam("uid") Integer uid) {
//        return fileMultipartService.uploadImage(imageFiles, uid);
//    }
}
