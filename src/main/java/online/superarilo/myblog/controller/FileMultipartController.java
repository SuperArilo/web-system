package online.superarilo.myblog.controller;

import online.superarilo.myblog.service.IFileMultipartService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileMultipartController {

    @Autowired
    private IFileMultipartService fileMultipartService;

    @PostMapping("/upload/image")
    public Result<Map<String, String>> uploadImage(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("business") String business, HttpServletResponse response) {
        return fileMultipartService.uploadImage(imageFile, business, response);
    }
}
