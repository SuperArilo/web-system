package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.mapper.DynamicCommentsMapper;
import online.superarilo.myblog.service.IDynamicCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @since 2022-02-18
 * 该类已弃用 替代为 online.superarilo.myblog.service.impl.DynamicCommentServiceImpl
 */
//@Service
public class DynamicCommentsServiceImpl extends ServiceImpl<DynamicCommentsMapper, DynamicComments> implements IDynamicCommentsService {


    private IUsersDynamicsService dynamicsService;

    @Autowired
    public void setDynamicsService(IUsersDynamicsService dynamicsService) {
        this.dynamicsService = dynamicsService;
    }


    @Override
    public Map<String, Object> listCommentsByDynamicId(Long commentParentId, Long dynamicId, Integer pageStart, Integer pageSize) {
        UsersDynamics dynamics = dynamicsService.getById(dynamicId);

        if(dynamics == null) {
            return null;
        }

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("commentParentId", commentParentId);
        queryParams.put("dynamicId", dynamicId);
        queryParams.put("pageStart", pageStart);
        queryParams.put("pageSize", pageSize);

        List<DynamicCommentsVO> dynamicCommentsEntities = this.baseMapper.listCommentsByDynamicId(queryParams);

        if(commentParentId == 0) {
            for (DynamicCommentsVO dynamicCommentsVO : dynamicCommentsEntities) {
                Map<String, Object> childrenResult = this.listCommentsByDynamicId(dynamicCommentsVO.getId(),
                        dynamicCommentsVO.getDynamicId(),
                        pageStart,
                        pageSize);
                dynamicCommentsVO.setChildren(childrenResult);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", dynamicCommentsEntities);
        map.put("total", this.baseMapper.dynamicCommentCount(queryParams));
        return map;
    }

    @Override
    public Result<String> commentByDynamicId(Long dynamicId, DynamicComments dynamicComments, HttpServletRequest request) {
        if(Objects.isNull(dynamicId) || Objects.isNull(dynamicsService.getOne(new QueryWrapper<UsersDynamics>().lambda().eq(UsersDynamics::getId, dynamicId)))) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "未找到评论的动态", null);
        }

        if(!StringUtils.hasLength(dynamicComments.getCommentContent().trim())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "评论内容不能为空", null);
        }

        if(Objects.isNull(dynamicComments.getCommentParentId())) {
            dynamicComments.setCommentParentId(0L);
        }

        String token = request.getHeader("token");
        if(!org.apache.shiro.util.StringUtils.hasLength(token)) {
            new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录", null);
        }
        UserInformation user = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        if(Objects.isNull(user)) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录", null);
        }
        dynamicComments.setUid(user.getUid());
        dynamicComments.setDynamicId(dynamicId);
        this.save(dynamicComments);
        return new Result<>(true, HttpStatus.OK, "评论成功", null);
    }
}
