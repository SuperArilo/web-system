package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.DynamicComment;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-18
 */
public interface IDynamicCommentService extends IService<DynamicComment> {


    /**
     * 查询指定动态的评论数据
     *
     * @param dynamicId 动态id
     * @param pageStart 分页起始量
     * @param pageSize  分页数量
     * @return 结果
     */
    Result<Map<String, Object>> listCommentsByDynamicId(Long dynamicId, Integer pageStart, Integer pageSize);

    /**
     * 用户评论
     */
    Result<String> commentByDynamicId(Long dynamicId, DynamicCommentVO dynamicCommentVO, HttpServletRequest request);
}
