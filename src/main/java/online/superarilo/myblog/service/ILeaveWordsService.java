package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.LeaveWords;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.JsonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-21
 */
public interface ILeaveWordsService extends IService<LeaveWords> {

    /**
     * 查询所有留言信息
     */
    JsonResult listLeaveWords(Integer pageNumber, Integer pageSize);

    /**
     * 用户留言
     */
    JsonResult userLeaveWords(LeaveWords leaveWords, HttpServletRequest request);
}
