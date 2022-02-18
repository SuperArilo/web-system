package online.superarilo.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.entity.DynamicComment;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.mapper.DynamicCommentMapper;
import online.superarilo.myblog.service.IDynamicCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
