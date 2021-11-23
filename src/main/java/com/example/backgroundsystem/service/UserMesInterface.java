package com.example.backgroundsystem.service;

import java.util.List;
import java.util.Map;

public interface UserMesInterface {
    Object userMessageGet(Integer page,Integer size);
    Map<String,Object> userMessageSet(Integer userId,String content);
    Map<String,Object> userMessageDel(Integer userId, List<Integer> id);
}
