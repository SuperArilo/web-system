package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.UserInformation;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
