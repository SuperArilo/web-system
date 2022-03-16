package online.superarilo.myblog.controller;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import online.superarilo.myblog.annotation.Log;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.FileMultipartUtil;
import online.superarilo.myblog.utils.JsonResult;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.ImageRelativeAbsolutePathVO;

/**
 * <p>
 * 前端控制器
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

    @Log
    @GetMapping("/information")
    public Result<Map<String, Object>> queryUserInfo(HttpServletRequest request) {
        return userInformationService.queryUserInfo(request);
    }

    /**
     * 用户修改个人信息
     */
    @Log
    @PostMapping("/{uid}")
    public Result<String> updateUserInfo(@PathVariable("uid") Long uid, @RequestBody UserInformation user,
        HttpServletRequest request) {
        if (Objects.isNull(user.getUid())) {
            user.setUid(uid);
        }
        return userInformationService.updateUserInfo(user, request);
    }

    /***
     * 上传头像
     * 
     * @param headerFile
     *            文件
     * @return 路径
     */
    @Log
    @PostMapping("/header/upload")
    public Result<ImageRelativeAbsolutePathVO> uploadHeader(@RequestParam("headerFile") MultipartFile headerFile,
        HttpServletRequest request) {
        if (headerFile != null && !headerFile.isEmpty()) {
            String token = request.getHeader("token");
            UserInformation user = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
            if (user == null) {
                return new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录");
            }
            ImageRelativeAbsolutePathVO imageRelativeAbsolutePathVO = FileMultipartUtil.uploadHeader(headerFile);
            if (imageRelativeAbsolutePathVO != null) {
                UserInformation userInformation = new UserInformation();
                userInformation.setUid(user.getUid());
                userInformation.setUserhead(imageRelativeAbsolutePathVO.getAbsolutePath());
                userInformationService.updateById(userInformation);
                return new Result<>(true, HttpStatus.OK, "上传成功", imageRelativeAbsolutePathVO);
            }
        }
        return new Result<>(false, HttpStatus.BAD_REQUEST, "上传失败", null);
    }

    /**
     * Minecraft ID验证
     *
     * @param javaMcId
     *            录入ID
     * @return Result<?>
     */
    @Log
    @PostMapping("/whitelist")
    public JsonResult whitelist(@RequestParam String javaMcId, HttpServletRequest request) {

        String token = request.getHeader("token");
        UserInformation adminInfo = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);

        if (javaMcId == null) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "参数错误");
        } else if (javaMcId.length() < 1) {
            return JsonResult.OK("请输入要绑定的ID");
        }

        return userInformationService.whitelist(javaMcId, adminInfo);
    }

    @Log
    @PutMapping("/updateWhitelist")
    public JsonResult updateWhitelist(HttpServletRequest request) {

        String token = request.getHeader("token");
        UserInformation adminInfo = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);

        return userInformationService.updateWhitelist(adminInfo);

    }

}
