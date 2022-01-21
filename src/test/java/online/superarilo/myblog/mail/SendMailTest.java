package online.superarilo.myblog.mail;

import online.superarilo.myblog.utils.EmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailTest {

    @Test
    public void sendMail() {
        EmailUtil.sendEmail("cao184771@aliyun.com", "验证码", "123456");
    }
}
