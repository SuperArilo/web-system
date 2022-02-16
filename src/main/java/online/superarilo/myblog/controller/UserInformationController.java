package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.FileMultipartUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.ImageRelativeAbsolutePathVO;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

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
    public Result<String> updateUserInfo(@PathVariable("uid") Long uid, @RequestBody UserInformation user, HttpServletRequest request) {
        if(Objects.isNull(user.getUid())) {
            user.setUid(uid);
        }
        return userInformationService.updateUserInfo(user, request);
    }


    /***
     * 上传头像
     * @param headerFile 文件
     * @return 路径
     */
    @PostMapping("/header/upload")
    public Result<ImageRelativeAbsolutePathVO> uploadHeader(@RequestParam("headerFile") MultipartFile headerFile) {
        if(headerFile != null && !headerFile.isEmpty()) {
            ImageRelativeAbsolutePathVO imageRelativeAbsolutePathVO = FileMultipartUtil.uploadHeader(headerFile);
            return new Result<>(true, HttpStatus.OK, "上传成功", imageRelativeAbsolutePathVO);
        }
        return new Result<>(false, HttpStatus.BAD_REQUEST, "上传失败", null);
    }
}
