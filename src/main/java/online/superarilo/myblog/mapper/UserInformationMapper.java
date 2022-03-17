package online.superarilo.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.superarilo.myblog.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Mapper
public interface UserInformationMapper extends BaseMapper<UserInformation> {


    Map<String, Object> queryUserInfo(@Param("uid") Long uid);

    Integer updateMcUuid(@Param("uuid") String uuid, @Param("name") String user, @Param("id") Long uid);

    Integer updateWhitelist(@Param("id") Long uid);

    void insertMcWhitelist(@Param("id") Long uid, @Param("userName") String javaMcId, @Param("userUuid") String uuid);

    void deleteMcWhitelist(@Param("id") Long uid);
}
