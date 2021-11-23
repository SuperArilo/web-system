package com.example.backgroundsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backgroundsystem.entity.DynamicComment;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DynamicCommentMapper extends BaseMapper<DynamicComment> {
    @MapKey("commentid")
    List<Map<String, Object>> commentGetByPage(@Param("leftvalue") Integer page, @Param("rightvalue") Integer size,@Param("dynamicid")Integer dynamicId);
}
