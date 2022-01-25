package online.superarilo.myblog.service.impl;

import online.superarilo.myblog.service.IMailService;
import online.superarilo.myblog.utils.MailUtil;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class MailServiceImpl implements IMailService {

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment env) {
        environment = env;
    }

    public Result<String> sendMail(String to) {
        to = to.trim();
        if(to.length() == 0) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "请输入邮箱", null);
        }
        if(!to.matches(MailUtil.MAIL_REGEX)) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "请输入正确的邮箱", null);
        }
        String titleTemplate = environment.getProperty("spring.mail.title-template");
        String contentTemplate = environment.getProperty("spring.mail.content-template");
        String code = getCode();
        String title;
        String result;

        try {
            title = Objects.requireNonNull(titleTemplate).replaceAll("[?]{3}", code);
            result = Objects.requireNonNull(contentTemplate).replaceAll("[?]{3}", code);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new Result<>(false, HttpStatus.INTERNAL_SERVER_ERROR, "验证码发送失败，请稍后重试", null);
        }

        boolean sent = MailUtil.sendMailHtml(title, result, to);
        if(!sent) {
            return new Result<>(false, HttpStatus.INTERNAL_SERVER_ERROR, "验证码发送失败，请稍后重试", null);
        }

        RedisUtil.set(to, code, 5 * 60, TimeUnit.SECONDS);

        return new Result<>(true, HttpStatus.OK, "验证码发送成功", null);
    }

    private String getCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }
}
