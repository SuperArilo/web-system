package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import online.superarilo.myblog.entity.LeaveWords;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.LeaveWordsMapper;
import online.superarilo.myblog.service.ILeaveWordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.utils.JsonResult;
import online.superarilo.myblog.utils.PageUtils;
import online.superarilo.myblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-21
 */
@Service
public class LeaveWordsServiceImpl extends ServiceImpl<LeaveWordsMapper, LeaveWords> implements ILeaveWordsService {

    private LeaveWordsMapper leaveWordsMapper;

    @Autowired
    public void setLeaveWordsMapper(LeaveWordsMapper leaveWordsMapper) {
        this.leaveWordsMapper = leaveWordsMapper;
    }

    @Override
    public JsonResult listLeaveWords(Integer pageNumber, Integer pageSize) {
        if(Objects.isNull(pageNumber) || Objects.isNull(pageSize) || pageNumber <= 0 || pageSize <= 0) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "分页参数不正确");
        }

        PageHelper.startPage(pageNumber, pageSize);
        List<LeaveWords> list = leaveWordsMapper.listLeaveWords();
        return JsonResult.PAGE(new PageUtils(new PageInfo<>(list)));
    }

    @Override
    public JsonResult userLeaveWords(LeaveWords leaveWords, HttpServletRequest request) {

        if(Objects.isNull(leaveWords)) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "无数据");
        }
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)) {
            return JsonResult.ERROR(HttpStatus.UNAUTHORIZED.value(), "请先登录");
        }
        UserInformation userInformation = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        if(Objects.isNull(userInformation)) {
            return JsonResult.ERROR(HttpStatus.UNAUTHORIZED.value(), "登录失效，请重新登录");
        }
        Long uid = userInformation.getUid();
        if(!StringUtils.hasLength(leaveWords.getContent().trim())) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "请输入留言内容");
        }

        LeaveWords word = new LeaveWords();
        word.setUid(uid);
        word.setContent(leaveWords.getContent().trim());
        word.setCreateTime(new Date());
        try {
            boolean save = this.save(word);
            if(save) {
                return JsonResult.OK("成功留言");
            }else {
                return JsonResult.ERROR(HttpStatus.SERVICE_UNAVAILABLE.value(), "留言失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.ERROR(HttpStatus.SERVICE_UNAVAILABLE.value(), "服务器繁忙，请稍后重试");
        }
    }
}
