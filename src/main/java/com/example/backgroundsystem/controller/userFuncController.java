package com.example.backgroundsystem.controller;

import com.example.backgroundsystem.entity.Result;
import com.example.backgroundsystem.service.UserFuncInterface;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/function")
public class userFuncController {
    final
    UserFuncInterface userFuncInterface;
    public userFuncController(UserFuncInterface userFuncInterface) {
        this.userFuncInterface = userFuncInterface;
    }
    @GetMapping("/get")
    public Result<?> userGet(@RequestParam(value = "email",required = false)String email,@RequestParam(value = "userpwd",required = false)String userPassWord) {
//        if (email == null || userPassWord == null){
//            return new Result<>(false,HttpStatus.OK,"服务器没有收到任何的参数指令！");
//        }
//        return new Result<>(true, HttpStatus.OK,"服务器成功响应！",userFuncInterface.getUserAll(email,userPassWord));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email,userPassWord);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return new Result<>(false, HttpStatus.OK, "未知账户！");
        }
        if (subject.isAuthenticated()) {
            return new Result<>(true, HttpStatus.OK, "登录成功！");
        } else {
            token.clear();
            return new Result<>(false, HttpStatus.OK, "登录失败");
        }
    }
}
