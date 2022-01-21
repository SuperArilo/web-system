package online.superarilo.myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    private static JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender jms) {
        javaMailSender = jms;
    }

    private static Environment environment;
    @Autowired
    public void setEnvironment(Environment env) {
        environment = env;
    }

    public static boolean sendEmail(String to, String title, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String from = environment.getProperty("spring.mail.username");
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
        return true;
    }
}
