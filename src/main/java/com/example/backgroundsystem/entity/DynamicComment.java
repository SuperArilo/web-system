package com.example.backgroundsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user_dynamic_comment")
public class DynamicComment {
    @TableId(type = IdType.AUTO,value = "commentid")
    private Integer commentid;
    @TableField(value = "uid")
    private int uId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "createtime")
    private Date createTime;
    @TableField(value = "likesum")
    private int likeSum;
    @TableField(value = "unlikesum")
    private int unLikeSum;
}
