package online.superarilo.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.dto.UserDTO;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IRegisterService;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.MailUtil;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.RegexUtil;
import online.superarilo.myblog.utils.Result;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

@Service
public class RegisterServiceImpl implements IRegisterService {

    private IUserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(IUserInformationService us) {
        userInformationService = us;
    }

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment env) {
        environment = env;
    }

    private static final String DEFAULT_HEADER_URL_KEY = "user.default-header.url";

    @Override
    public Result<String> register(UserDTO userDTO) {
        if(Objects.isNull(userDTO)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "请输入正确参数", null);
        }
        if(!StringUtils.hasLength(userDTO.getMail().trim()) || !userDTO.getMail().trim().matches(MailUtil.MAIL_REGEX)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "请输入正确邮箱");
        }
        // 判断邮箱是否注册
        UserInformation hasUser = userInformationService.findUserByUsername(userDTO.getMail().trim().toLowerCase());
        if(Objects.nonNull(hasUser)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "邮箱已注册");
        }
        if(!StringUtils.hasLength(userDTO.getPassword().trim()) || !userDTO.getPassword().trim().matches(RegexUtil.ALPHANUMERIC_CHARACTERS_AND_SPECIAL_CHARACTERS)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "密码至少为字母、数字、和特殊字符@./*-+%$#中的前两种6-16个字符");
        }
        if(!userDTO.getPassword().trim().equals(userDTO.getConfirm())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "确认密码不一致");
        }
        if(!StringUtils.hasLength(userDTO.getMailVerifyCode().trim()) || !userDTO.getMailVerifyCode().trim().equalsIgnoreCase(String.valueOf(RedisUtil.get(userDTO.getMail().trim())))) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "邮箱验证码不正确");
        }
        String verifyKey = "register" + userDTO.getRandom() + RedisUtil.VERIFY_CODE_REDIS_KEY_SUFFIX;
        Object o = RedisUtil.get(verifyKey);
        if(!StringUtils.hasLength(userDTO.getVerifyCode().trim()) || !userDTO.getVerifyCode().trim().equalsIgnoreCase(String.valueOf(RedisUtil.get(verifyKey)))) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "验证码不正确");
        }

        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(userDTO.getMail().trim());
        userInformation.setEmail(userInformation.getUsername());
        userInformation.setRegistertime(new Date());
        userInformation.setUserhead(environment.getProperty(DEFAULT_HEADER_URL_KEY));
        userInformation.setUserpwd(new Md5Hash(userDTO.getPassword().trim(), userDTO.getMail().trim(), 2).toString());
        userInformationService.save(userInformation);

        // 删除验证码
        RedisUtil.delete(userInformation.getUsername());
        RedisUtil.delete(verifyKey);
        return new Result<>(true, HttpStatus.OK, "注册成功");
    }
}
