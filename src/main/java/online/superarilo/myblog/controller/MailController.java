package online.superarilo.myblog.controller;

import online.superarilo.myblog.utils.EmailUtil;
import online.superarilo.myblog.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class MailController {


    @PostMapping("/code")
    public Result<String> sendMail(String to) {

        String result = "Hi，亲爱的玩家\n\n你正在注册王勇强的博客账号，验证码为：" + getCode() + "\n\n请在5分钟内完成验证\n\nSuperArilo\n\n此为系统邮件，切勿回复";
        EmailUtil.sendEmail(to, "验证码", result);
        return new Result<>(true, HttpStatus.OK, "发送成功", null);
    }




    private String getCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }
}
