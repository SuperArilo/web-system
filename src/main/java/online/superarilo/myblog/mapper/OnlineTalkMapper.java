package online.superarilo.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface OnlineTalkMapper {
    Map<String,Object> getUserHeadAndName(@Param("mcJavaId") String mcJavaId, @Param("uuid") String uuid);
}
