package online.superarilo.myblog.service.impl;

import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.mapper.DynamicCommentsMapper;
import online.superarilo.myblog.service.IDynamicCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentsVO;
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
 * @since 2022-01-08
 */
@Service
public class DynamicCommentsServiceImpl extends ServiceImpl<DynamicCommentsMapper, DynamicComments> implements IDynamicCommentsService {

    @Autowired
    private IUsersDynamicsService dynamicsService;

    @Override
    public Result<List<DynamicCommentsVO>> listCommentsByDynamicId(Integer commentParentId, Integer dynamicId, Integer pageStart, Integer pageSize) {
        UsersDynamics dynamics = dynamicsService.getById(dynamicId);

        if(dynamics == null) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "资源不存在", null);
        }

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("commentParentId", commentParentId);
        queryParams.put("dynamicId", dynamicId);
        queryParams.put("pageStart", pageStart);
        queryParams.put("pageSize", pageSize);

        List<DynamicCommentsVO> dynamicCommentsEntities = this.baseMapper.listCommentsByDynamicId(queryParams);

        if(commentParentId == 0) {
            for (DynamicCommentsVO dynamicCommentsVO : dynamicCommentsEntities) {
                Result<List<DynamicCommentsVO>> childrenResult = this.listCommentsByDynamicId(dynamicCommentsVO.getId(),
                        dynamicCommentsVO.getDynamicId(),
                        pageStart,
                        pageSize);
                dynamicCommentsVO.setChildren(childrenResult.getData());
            }
        }

        return new Result<>(false, HttpStatus.OK, "success", dynamicCommentsEntities);
    }
}
