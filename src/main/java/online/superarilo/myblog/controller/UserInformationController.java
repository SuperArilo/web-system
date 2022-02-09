package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public Result<UserInformation> queryUserInfo(HttpServletRequest request) {
        return userInformationService.queryUserInfo(request);
    }
}
