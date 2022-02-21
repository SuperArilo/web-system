package online.superarilo.myblog.controller;

import online.superarilo.myblog.annotation.Log;
import online.superarilo.myblog.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerMessageGetController {

    @Log
    @PostMapping("/player/message/get")
    public Result<?> getMessage(@RequestParam(value = "user",required = false)String playerName,@RequestParam(value = "message",required = false)String messageContent){
        System.out.println("发送名   " + playerName);
        System.out.println("内容   " + messageContent);
        return new Result<>(true, HttpStatus.OK,"接受成功",null);
    }
}
