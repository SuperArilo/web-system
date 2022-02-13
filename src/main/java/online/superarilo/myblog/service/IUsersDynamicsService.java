package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.entity.UsersDynamics;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.UsersDynamicsVO;

import javax.servlet.http.HttpServletRequest;
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
     * 查询条数
     * @param queryParams
     * @return
     */
    Long dynamicListCount(Map<String, Object> queryParams);

    /**
     * 根据id查询详情
     * @param dynamicId
     * @return
     */
    Result<UsersDynamicsVO> queryDynamicById(Long dynamicId);

    /**
     * 发布动态
     * @param usersDynamicsVO
     * @return
     */
    Result<String> saveUserDynamic(UsersDynamicsVO usersDynamicsVO);

    /**
     * 动态浏览量递增
     * @param dynamicId
     * @param request
     * @return
     */
    Result<String> incrementDynamicPageView(Long dynamicId, HttpServletRequest request);
}
