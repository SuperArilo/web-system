package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.DynamicComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.superarilo.myblog.vo.DynamicCommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-18
 */
@Mapper
public interface DynamicCommentMapper extends BaseMapper<DynamicComment> {
    /**
     * 查询指定动态的评论数据
     * @return 结果
     */
    List<DynamicCommentVO> listComments(Map<String, Object> queryParams);

}
