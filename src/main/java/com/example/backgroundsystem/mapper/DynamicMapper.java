package com.example.backgroundsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backgroundsystem.entity.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DynamicMapper extends BaseMapper<Dynamic> {
    List<Map<String, Object>> dynamicGet(@Param("leftvalue") Integer page, @Param("rightvalue") Integer size);
    void dynamicDel(@Param("userid")Integer userId, @Param("dynamicidlist")List<Integer> dynamicIdList);
    List<String> dynamicImageNameGet(@Param("dynamicIdList") List<Integer> dynamicIdList);
}
