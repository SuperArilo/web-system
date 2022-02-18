package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.DynamicComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.superarilo.myblog.vo.DynamicCommentsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */

/**
 * @since 2022-02-18
 * 该接口已弃用 替代为 online.superarilo.myblog.mapper.DynamicCommentMapper
 */

//@Mapper
public interface DynamicCommentsMapper extends BaseMapper<DynamicComments> {

    /**
     * 查询指定动态的评论数据
     * @return 结果
     */
    List<DynamicCommentsVO> listCommentsByDynamicId(Map<String, Object> queryParams);

    /**
     * 放回动态评论数
     * @param queryParams 条件
     * @return 数量
     */
    Long dynamicCommentCount(Map<String, Object> queryParams);
}
