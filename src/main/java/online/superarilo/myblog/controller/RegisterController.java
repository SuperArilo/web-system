package online.superarilo.myblog.controller;


import online.superarilo.myblog.annotation.Log;
import online.superarilo.myblog.dto.UserDTO;
import online.superarilo.myblog.service.IRegisterService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 注册
 */

@RestController
@RequestMapping("/register")
public class RegisterController {

    private IRegisterService registerService;

    @Autowired
    public void setRegisterService(IRegisterService rs) {
        registerService = rs;
    }

    @Log
    @PostMapping("/user")
    public Result<String> registerUser(@RequestBody UserDTO userDTO) {
        return registerService.register(userDTO);
    }

}
