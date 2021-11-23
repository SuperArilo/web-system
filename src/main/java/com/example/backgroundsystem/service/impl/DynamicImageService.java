package com.example.backgroundsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backgroundsystem.entity.DynamicImage;
import com.example.backgroundsystem.mapper.DynamicImageMapper;
import com.example.backgroundsystem.service.DynamicImageInterface;
import org.springframework.stereotype.Service;


@Service
public class DynamicImageService extends ServiceImpl<DynamicImageMapper, DynamicImage> implements DynamicImageInterface {
}
