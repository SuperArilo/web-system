package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.*;
import online.superarilo.myblog.mapper.DynamicCommentMapper;
import online.superarilo.myblog.service.IDynamicCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IInformService;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentVO;
import online.superarilo.myblog.vo.InformVO;
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

    private IInformService informService;

    @Autowired
    public void setInformService(IInformService informService) {
        this.informService = informService;
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
        UsersDynamics one = dynamicsService.getOne(new QueryWrapper<UsersDynamics>().lambda().eq(UsersDynamics::getId, dynamicId));
        if(Objects.isNull(one)) {
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
        dynamicComment.setByReplyId(dynamicCommentVO.getReplyId());
        dynamicComment.setReplyContent(dynamicCommentVO.getReplyContent());
        dynamicComment.setReplyTime(new Date());
        this.save(dynamicComment);

        // 通知对方
        InformVO inform = new InformVO();
        inform.setReceiver(Objects.isNull(dynamicCommentVO.getByReplyId()) ? one.getUid() : dynamicCommentVO.getReplyId());
        inform.setRead(0);
        inform.setSys(0);
        inform.setCreateTime(new Date());
        inform.setContent(null);
        inform.setEventId(dynamicId);
        inform.setNotifier(dynamicComment.getReplyId());
        informService.addInform(inform);
        return new Result<>(true, HttpStatus.OK, "评论成功", null);
    }
}
