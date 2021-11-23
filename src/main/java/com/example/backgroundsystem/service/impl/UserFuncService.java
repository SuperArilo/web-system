package com.example.backgroundsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backgroundsystem.entity.User;
import com.example.backgroundsystem.mapper.UserFuncMapper;
import com.example.backgroundsystem.service.UserFuncInterface;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserFuncService extends ServiceImpl<UserFuncMapper, User> implements UserFuncInterface {
    final
    UserFuncMapper userFuncMapper;
    public UserFuncService(UserFuncMapper userFuncMapper) {
        this.userFuncMapper = userFuncMapper;
    }
    @Override
    public Map<String, Object> getUserAll(String email, String userPassword) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = userFuncMapper.getUserInf(email,userPassword);
        if (map == null){
            returnMap.put("message","用户名或者密码错误！");
            returnMap.put("result",false);
        } else {
            returnMap.put("message","登陆成功！");
            returnMap.put("result",true);
        }
        return returnMap;
    }
}
