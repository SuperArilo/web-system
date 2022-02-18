package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.DynamicComments;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @since 2022-02-18
 * 该接口已弃用 替代为 online.superarilo.myblog.service.IDynamicCommentService
 */
public interface IDynamicCommentsService extends IService<DynamicComments> {

    /**
     * 查询指定动态的评论数据
     *
     * @param dynamicId 动态id
     * @param pageStart 分页起始量
     * @param pageSize  分页数量
     * @return 结果
     */
    Map<String, Object> listCommentsByDynamicId(Long commentParentId, Long dynamicId, Integer pageStart, Integer pageSize);

    /**
     * 用户评论
     */
    Result<String> commentByDynamicId(Long dynamicId, DynamicComments dynamicComments, HttpServletRequest request);
}
