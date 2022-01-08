package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.UsersDynamics;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.UsersDynamicsVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
public interface IUsersDynamicsService extends IService<UsersDynamics> {
    /**
     * 根据条件查询所有动态信息
     * @return
     */
    List<UsersDynamicsVO> listUserDynamics(Map<String, Object> queryParams);

    /**
     * 发布动态
     * @param usersDynamicsVO
     * @return
     */
    Result<String> saveUserDynamic(UsersDynamicsVO usersDynamicsVO);
}
