package online.superarilo.myblog.service;

import online.superarilo.myblog.utils.Result;

public interface IMailService {

    /**
     * 发送邮箱验证码
     * @param to 收件人
     * @return 响应数据
     */
    Result<String> sendMail(String to);
}
