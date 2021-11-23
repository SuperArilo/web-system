package com.example.backgroundsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data

@TableName(value = "user_dynamic_image")
public class DynamicImage {
    @TableId(type = IdType.AUTO,value = "imageid")
    private int imageId;
    @TableField(value = "dynamicid")
    private int dynamicId;
    @TableField(value = "imagename")
    private String imageName;
    @TableField(value = "imageurl")
    private String imageUrl;
}
