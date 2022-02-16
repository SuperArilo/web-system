package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.UserInformationMapper;
import online.superarilo.myblog.service.IUserInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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

    private UserInformationMapper userInformationMapper;

    @Autowired
    public void setUserInformationMapper(UserInformationMapper userInformationMapper) {
        this.userInformationMapper = userInformationMapper;
    }

    @Override
    public UserInformation findUserByUsername(String username) {
        if(!StringUtils.hasLength(username)) {
            return null;
        }
        return this.getOne(new QueryWrapper<UserInformation>().lambda().eq(UserInformation::getUsername, username));
    }

    @Override
    public Result<Map<String, Object>> queryUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "invalid token", null);
        }
        UserInformation userInformation = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        if(Objects.isNull(userInformation)) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录", null);
        }
        return new Result<>(true, HttpStatus.OK, "查询成功", userInformationMapper.queryUserInfo(userInformation.getUid()));
    }

    @Override
    public Result<String> updateUserInfo(UserInformation user, HttpServletRequest request) {
        if(Objects.isNull(user) || Objects.isNull(user.getUid())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "修改失败");
        }
        UserInformation login;
        try {
            String token = request.getHeader("token");
            login = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录");
        }
        if(!Objects.equals(login.getUid(), user.getUid())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "不可以修改别人信息哦");
        }
        UserInformation userInformation = new UserInformation();
        if(!Objects.isNull(user.getNickname()) && user.getNickname().length() > 100) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "昵称长度过长，请小于100字");
        }
        if(!Objects.isNull(user.getPersonalizedSignature()) && user.getPersonalizedSignature().length() > 200) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "个性签名长度过长，请小于200字");
        }

        // todo 如果还需要修改其他字段 添加即可
        userInformation.setUid(user.getUid());
        userInformation.setNickname(StringUtils.hasLength(user.getNickname()) ? user.getNickname() : "");
        userInformation.setPersonalizedSignature(StringUtils.hasLength(user.getPersonalizedSignature()) ? user.getPersonalizedSignature() : "");

        boolean b = this.updateById(userInformation);
        if (b) {
            return new Result<>(true, HttpStatus.OK, "修改成功");
        }

        return new Result<>(false, HttpStatus.BAD_REQUEST, "修改失败");
    }
}
