package online.superarilo.myblog.realm;

import com.alibaba.fastjson.JSONObject;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.impl.UserInformationServiceImpl;
import online.superarilo.myblog.utils.RedisUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserRealm extends AuthorizingRealm {


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 登录认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        String userStr = (String) RedisUtil.get(accessToken);

        if(!StringUtils.hasLength(userStr)) {
            throw new IncorrectCredentialsException("会话失效，请重新登录");
        }

        UserInformation usersEntity = JSONObject.parseObject(userStr, UserInformation.class);

        return new SimpleAuthenticationInfo(usersEntity, accessToken, getName());
    }
}
