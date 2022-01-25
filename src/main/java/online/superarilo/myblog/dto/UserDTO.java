package online.superarilo.myblog.dto;

import lombok.Data;

/**
 * 注册用户数据传输类
 */
@Data
public class UserDTO {

    /**
     * 邮箱号
     */
    private String mail;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirm;

    /**
     * 邮箱验证码
     */
    private String mailVerifyCode;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 随机值
     */
    private String random;

}
