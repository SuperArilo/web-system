package com.example.backgroundsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backgroundsystem.entity.DynamicComment;
import com.example.backgroundsystem.mapper.DynamicCommentMapper;
import com.example.backgroundsystem.service.DynamicCommentInterface;
import org.springframework.stereotype.Service;

@Service
public class DynamicCommentService extends ServiceImpl<DynamicCommentMapper, DynamicComment> implements DynamicCommentInterface {
    final
    DynamicCommentMapper dynamicCommentMapper;

    public DynamicCommentService(DynamicCommentMapper dynamicCommentMapper) {
        this.dynamicCommentMapper = dynamicCommentMapper;
    }

    @Override
    public Object dynamicCommentGetByPage(Integer page, Integer size,Integer dynamicId) {
        return dynamicCommentMapper.commentGetByPage((page - 1) * size, size,dynamicId);
    }
}
