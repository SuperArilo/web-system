package online.superarilo.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.dto.UserDTO;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.generator.TokenGenerator;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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


        RedisUtil.delete(verifyKey);

        UserInformation userByUsername = userInformationService.findUserByUsername(user.getMail().trim());
        if(Objects.isNull(userByUsername)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "用户不存在", null);
        }

        UserInformation userInformation = userInformationService.getOne(new QueryWrapper<UserInformation>()
                .lambda()
                .eq(UserInformation::getUsername, user.getMail().trim())
                .eq(UserInformation::getUserpwd, new Md5Hash(user.getPassword().trim(), user.getMail().trim(), 2).toString()));
        if(Objects.isNull(userInformation)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "用户名或密码不正确", null);
        }
        userInformation.setUserpwd(null);
        String token = TokenGenerator.generateToken();
        RedisUtil.set(token, JSONObject.toJSONString(userInformation), 30 * 60, TimeUnit.SECONDS);

        Map<String, Object> map = new HashMap<>();
        map.put("user", userInformation);
        map.put("token", token);

        return new Result<>(true, HttpStatus.OK, "登录成功", map);
    }
}
