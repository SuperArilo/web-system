package com.example.backgroundsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backgroundsystem.entity.DynamicComment;

public interface DynamicCommentInterface extends IService<DynamicComment> {
    Object dynamicCommentGetByPage(Integer page,Integer size,Integer dynamicId);
}
