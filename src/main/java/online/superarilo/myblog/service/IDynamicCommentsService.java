package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.DynamicComments;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
public interface IDynamicCommentsService extends IService<DynamicComments> {

    /**
     * 查询指定动态的评论数据
     *
     * @param dynamicId 动态id
     * @param pageStart 分页起始量
     * @param pageSize  分页数量
     * @return
     */
    Result<List<DynamicCommentsVO>> listCommentsByDynamicId(Integer commentParentId, Integer dynamicId, Integer pageStart, Integer pageSize);
}