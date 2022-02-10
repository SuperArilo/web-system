package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.UserInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Mapper
public interface UserInformationMapper extends BaseMapper<UserInformation> {


    Map<String, Object> queryUserInfo(@Param("uid") Long uid);
}
