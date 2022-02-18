package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.DynamicComment;
import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.mapper.DynamicCommentMapper;
import online.superarilo.myblog.service.IDynamicCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-18
 */
@Service
public class DynamicCommentServiceImpl extends ServiceImpl<DynamicCommentMapper, DynamicComment> implements IDynamicCommentService {

    private IUsersDynamicsService dynamicsService;

    @Autowired
    public void setDynamicsService(IUsersDynamicsService dynamicsService) {
        this.dynamicsService = dynamicsService;
    }

    private IUserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(IUserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @Override
    public Result<Map<String, Object>> listCommentsByDynamicId(Long dynamicId, Integer pageStart, Integer pageSize) {
        UsersDynamics dynamics = dynamicsService.getById(dynamicId);

        if(dynamics == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "动态不存在或已被举报");
        }

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("dynamicId", dynamicId);
        queryParams.put("pageStart", pageStart);
        queryParams.put("pageSize", pageSize);

        List<DynamicCommentVO> dynamicCommentList = this.baseMapper.listComments(queryParams);
        Map<String, Object> map = new HashMap<>();
        map.put("list", dynamicCommentList);
        map.put("total", this.count(new QueryWrapper<DynamicComment>().lambda().eq(DynamicComment::getDynamicId, dynamicId)));

        return new Result<>(true, HttpStatus.OK, "查询成功", map);
    }

    @Override
    public Result<String> commentByDynamicId(Long dynamicId, DynamicCommentVO dynamicCommentVO, HttpServletRequest request) {
        if(Objects.isNull(dynamicId) || Objects.isNull(dynamicsService.getOne(new QueryWrapper<UsersDynamics>().lambda().eq(UsersDynamics::getId, dynamicId)))) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "未找到评论的动态", null);
        }

        if(!StringUtils.hasLength(dynamicCommentVO.getReplyContent().trim())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "评论内容不能为空", null);
        }


        if(!Objects.isNull(dynamicCommentVO.getByReplyId()) && Objects.isNull(userInformationService.getOne(new QueryWrapper<UserInformation>()
                .lambda()
                .eq(UserInformation::getUid, dynamicCommentVO.getByReplyId())))) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "回复的用户不存在");
        }
        String token = request.getHeader("token");
        if(!org.apache.shiro.util.StringUtils.hasLength(token)) {
            new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录", null);
        }
        UserInformation user = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        if(Objects.isNull(user)) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录", null);
        }

        DynamicComment dynamicComment = new DynamicComment();
        dynamicComment.setDynamicId(dynamicId);
        dynamicComment.setReplyId(user.getUid());
        dynamicComment.setByReplyId(dynamicCommentVO.getByReplyId());
        dynamicComment.setReplyContent(dynamicCommentVO.getReplyContent());
        dynamicComment.setReplyTime(new Date());
        this.save(dynamicComment);
        return new Result<>(true, HttpStatus.OK, "评论成功", null);
    }
}
