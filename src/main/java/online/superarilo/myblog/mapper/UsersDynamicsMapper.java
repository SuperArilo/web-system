package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.UsersDynamics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.superarilo.myblog.vo.UsersDynamicsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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
public interface UsersDynamicsMapper extends BaseMapper<UsersDynamics> {

    /**
     * 根据条件查询所有动态信息
     * @return
     */
    List<UsersDynamicsVO> listUserDynamics(Map<String, Object> queryParams);

    /**
     * 根据id查询动态信息
     * @param dynamicId
     * @return
     */
    UsersDynamicsVO queryDynamicById(@Param("dynamicId") Integer dynamicId);
}
