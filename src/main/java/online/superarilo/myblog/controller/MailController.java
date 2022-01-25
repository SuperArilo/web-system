package online.superarilo.myblog.controller;

import online.superarilo.myblog.service.IMailService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class MailController {

    private IMailService mailService;

    @Autowired
    public void setMailService(IMailService ms) {
        mailService = ms;
    }

    @PostMapping("/code")
    public Result<String> sendMail(String to) {
        return mailService.sendMail(to);
    }


}
