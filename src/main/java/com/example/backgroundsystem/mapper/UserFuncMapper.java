package com.example.backgroundsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backgroundsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserFuncMapper extends BaseMapper<User> {
    Map<String,Object> getUserInf(@Param("email") String email,@Param("userpwd") String userPassWord);
}
