package com.example.backgroundsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backgroundsystem.entity.User;

import java.util.Map;

public interface UserFuncInterface extends IService<User> {
    Map<String, Object> getUserAll(String email, String userPassword);
}
