package com.example.backgroundsystem.controller;

import com.example.backgroundsystem.entity.Result;
import com.example.backgroundsystem.service.DynamicCommentInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dynamic/comment")
public class dynamicCommentController {
    final
    DynamicCommentInterface dynamicCommentInterface;
    public dynamicCommentController(DynamicCommentInterface dynamicCommentInterface) {
        this.dynamicCommentInterface = dynamicCommentInterface;
    }
    @GetMapping("/get")
    public Result<?> commentGetByPage(@RequestParam(value = "dynamicid",required = false)Integer dynamicId, @RequestParam(value = "page",required = false)Integer page, @RequestParam(value = "size",required = false)Integer size){
        if (dynamicId == null || page == null || size == null){
            return new Result<>(false, HttpStatus.OK,"服务器没有收到任何的参数指令！");
        }
        return new Result<>(true,HttpStatus.OK,"服务器成功响应！",dynamicCommentInterface.dynamicCommentGetByPage(page,size,dynamicId));
    }
}
