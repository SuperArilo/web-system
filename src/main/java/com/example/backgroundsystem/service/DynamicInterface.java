package com.example.backgroundsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backgroundsystem.entity.Dynamic;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface DynamicInterface extends IService<Dynamic> {
    Object dynamicGetFromPage(Integer page,Integer size);
    Map<String,Object> dynamicSet(Integer uid, String content, List<MultipartFile> listFile, HttpServletRequest request);
    void dynamicDel(Integer userId,List<Integer> dynamicIdList);
}
