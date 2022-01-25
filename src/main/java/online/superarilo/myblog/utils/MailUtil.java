package online.superarilo.myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {

    /**
     * 邮箱验证规则
     */
    public static final String MAIL_REGEX = "^[a-zA-Z0-9]+@([a-zA-Z0-9])+\\.[a-zA-Z]+$";


    private static JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender jms) {
        javaMailSender = jms;
    }

    @Autowired
    public void setEnvironment(Environment env) {
        from = env.getProperty("spring.mail.username");
    }

    private static String from;

    /**
     * 发送简单文本内容
     * @param to 收件人
     * @param title 标题
     * @param content 内容
     * @return 是否发送成功
     */
    public static boolean sendMailText(String title, String content, String... to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
        return true;
    }


    /**
     * 发送html
     * @param title 标题
     * @param content 可以使用html标签
     * @param to 收件人
     * @return 是否发送成功
     */
    public static boolean sendMailHtml(String title, String content, String... to) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(title);
            messageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean sendMailImage(String title, String content, String... to) {
        return true;
    }


    /**
     * 发送附件
     * @param title 标题
     * @param content 内容
     * @param to 收件人
     * @return 是否发送成功
     */
    public static boolean sendMailFile(String title, String content, String... to)  {
        return true;
    }
}
