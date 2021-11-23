package com.example.backgroundsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user_dynamic")
public class Dynamic {
    @TableId(type = IdType.AUTO,value = "dynamicid")
    private int dynamicId;
    @TableField(value = "userid")
    private int userId;
    @TableField(value = "createtime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyyMMddHHmmss")
    private Date createTime;
    @TableField(value = "content")
    private String content;
    @TableField(value = "likesum")
    private int likeSum;
    @TableField(value = "commentsum")
    private int commentSum;
    @TableField(value = "watchsum")
    private int watchSum;
}
