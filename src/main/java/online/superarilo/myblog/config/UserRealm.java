package online.superarilo.myblog.config;

import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private IUserInformationService userInformationService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserInformation user = userInformationService.findUserByUsername(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException();
        }

        //设置账户30分钟过期
        SecurityUtils.getSubject().getSession().setTimeout(30 * 60 * 60 * 1000);
        //登录成功
        SimpleAuthenticationInfo authenticationInfo;

//        try {
//            OAuthIssuer issuer = new OAuthIssuerImpl(new MD5Generator());
//            String accessToken = issuer.accessToken();
//            user.setToken(accessToken);
//            sysOAuthAuthorizeService.addAccessToken(accessToken, String.valueOf(user.getId()));
//        } catch (OAuthSystemException e) {
////			e.printStackTrace();
//        }

        authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户对象
                user.getUserpwd(), // 密码
                ByteSource.Util.bytes(token.getUsername()),// salt=username
                getName()); // realm name

        return authenticationInfo;
    }
}

