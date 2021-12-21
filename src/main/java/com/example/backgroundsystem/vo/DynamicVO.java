package com.example.backgroundsystem.vo;

import com.example.backgroundsystem.entity.Dynamic;
import lombok.Data;

import java.util.List;

/**
 * 动态展示区数据视图对象
 */
@Data
public class DynamicVO extends Dynamic {

    /**
     * 用户名称
     */
    private String username;

    /**
     * 头像地址
     */
    private String headAddress;

    /**
     * 用户类别
     */
    private Integer clazz;

    /**
     * 动态图片集合
     */
    private List<String> imageAddresses;

    /**
     * 动态点赞人名称集合
     */
    private List<String> usersWhoLikedIt;
}
