package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.LeaveWords;
import online.superarilo.myblog.service.ILeaveWordsService;
import online.superarilo.myblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-21
 */
@RestController

@RequestMapping("/leave-words")
public class LeaveWordsController {

    private ILeaveWordsService leaveWordsService;

    @Autowired
    public void setLeaveWordsService(ILeaveWordsService leaveWordsService) {
        this.leaveWordsService = leaveWordsService;
    }

    /**
     * 查询所有留言信息
     */


    /**
     * 用户留言
     */
    @PostMapping("/")
    public JsonResult userLeaveWords(HttpServletRequest request, @RequestBody LeaveWords leaveWords) {
        return leaveWordsService.userLeaveWords(leaveWords, request);
    }
}
