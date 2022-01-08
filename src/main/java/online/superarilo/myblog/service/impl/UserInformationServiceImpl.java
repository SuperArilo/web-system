package online.superarilo.myblog.service.impl;

import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.UserInformationMapper;
import online.superarilo.myblog.service.IUserInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Service
public class UserInformationServiceImpl extends ServiceImpl<UserInformationMapper, UserInformation> implements IUserInformationService {

}
