package com.example.backgroundsystem.controller;

import com.example.backgroundsystem.entity.Result;
import com.example.backgroundsystem.service.DynamicInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/dynamic")
public class dynamicController {

    @Autowired
    private DynamicInterface dynamicInterface;

    @GetMapping("/get")
    public Result<?> dynamicGet(@RequestParam(value = "page",required = false)Integer page, @RequestParam(value = "size",required = false)Integer size){
        if (page == null || size == null){
            return new Result<>(false, HttpStatus.OK,"服务器没收到任何参数指令！");
        }
        return new Result<>(true,HttpStatus.OK,"服务器成功响应！",dynamicInterface.dynamicGetFromPage(page,size));
    }
    @PostMapping("/set")
    public Result<?> dynamicSet(@RequestParam(value = "uid",required = false)Integer uid, @RequestParam(value = "content",required = false)String content, @RequestParam(value = "imagelist",required = false)List<MultipartFile> imageList, HttpServletRequest request){
        if (uid == null || content == null || imageList == null){
            return new Result<>(false, HttpStatus.OK,"服务器没收到任何参数指令！");
        }
        return new Result<>(true,HttpStatus.OK,"服务器成功响应！",dynamicInterface.dynamicSet(uid,content,imageList,request));
    }
    @PostMapping("/del")
    public Result<?> dynamicDel(@RequestParam(value = "userid")Integer userId,@RequestParam(value = "idlist",required = false)List<Integer> idList){
        if (userId == null || idList == null){
            return new Result<>(false, HttpStatus.OK,"服务器没收到任何参数指令！");
        }
        dynamicInterface.dynamicDel(userId,idList);
        return new Result<>(true,HttpStatus.OK,"服务器成功响应！");
    }
}
