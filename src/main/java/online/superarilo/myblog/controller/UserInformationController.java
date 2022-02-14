package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/user")
public class UserInformationController {


    private IUserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(IUserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @GetMapping("/information")
    public Result<Map<String, Object>> queryUserInfo(HttpServletRequest request) {
        return userInformationService.queryUserInfo(request);
    }

    /**
     * 用户修改个人信息
     */
    @PostMapping("/{uid}")
    public Result<String> updateUserInfo(@PathVariable("uid") Long uid, @RequestBody UserInformation user) {
        if(Objects.isNull(user.getUid())) {
            user.setUid(uid);
        }
        return userInformationService.updateUserInfo(user);
    }
}
