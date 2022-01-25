package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.UserInformation;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
