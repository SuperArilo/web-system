package com.example.backgroundsystem.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface NoteFileInterface {
    Object noteFilePageGet(Integer page,Integer size);
    Map<String,Object> noteFileCreate(String title,String synopsis,String content);
    Object noteFileContentGet(Integer id);
    Map<String,Object> noteIntoImageUpload(List<MultipartFile> listFile, HttpServletRequest request);
}
