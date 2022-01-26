package online.superarilo.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.dto.UserDTO;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class LoginController {

    private IUserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(IUserInformationService us) {
        userInformationService = us;
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody UserDTO user) {
        String verifyKey = "login" + user.getRandom() + RedisUtil.VERIFY_CODE_REDIS_KEY_SUFFIX;
        if(!StringUtils.hasLength(user.getVerifyCode().trim()) || !user.getVerifyCode().trim().equalsIgnoreCase(String.valueOf(RedisUtil.get(verifyKey)))) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "验证码错误！");
        }

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken;
            usernamePasswordToken = new UsernamePasswordToken(user.getMail().trim(), user.getPassword().trim());
            subject.login(usernamePasswordToken);
        } catch(UnknownAccountException e) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "未知登录用户名！");
        } catch(IncorrectCredentialsException e) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "用户名或密码错误！");
        } catch (AuthenticationException e) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "账户验证失败！");
        }
        RedisUtil.delete(verifyKey);
        UserInformation userInformation = userInformationService.getOne(new QueryWrapper<UserInformation>().lambda().eq(UserInformation::getUsername, user.getMail().trim()));
        userInformation.setUserpwd(null);
        return new Result<>(true, HttpStatus.OK, "登录成功", userInformation);
    }
}
