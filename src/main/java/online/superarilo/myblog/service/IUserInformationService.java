package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.UserInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.JsonResult;
import online.superarilo.myblog.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
public interface IUserInformationService extends IService<UserInformation> {


    UserInformation findUserByUsername(String username);


    /**
     * 根据token返回用户信息
     * @return
     */
    Result<Map<String, Object>> queryUserInfo(HttpServletRequest request);


    /**
     * 用户修改个人信息
     */
    Result<String> updateUserInfo(UserInformation user, HttpServletRequest request);

    /**
     * Minecraft ID验证
     *
     * @param javaMcId 录入ID
     * @param user 用户信息
     * @return Result<?>
     */
    JsonResult whitelist(String javaMcId, UserInformation user);
}
