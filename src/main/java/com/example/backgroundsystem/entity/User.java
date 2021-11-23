package com.example.backgroundsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "`user_information")
public class User {
    @TableId(type = IdType.AUTO,value = "id")
    private int Id;
    @TableField(value = "uid")
    private int uid;
    @TableField(value = "username")
    private String userName;
    @TableField(value = "userpwd")
    private String userPassword;
    @TableField(value = "email")
    private String email;
    @TableField(value = "class")
    private int userClass;
    @TableField(value = "userhead")
    private String userHead;
    @TableField(value = "imagename")
    private String imageName;
    @TableField(value = "registertime")
    private Date registerTime;
}
