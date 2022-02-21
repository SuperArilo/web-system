package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.LeaveWords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-21
 */
@Mapper
public interface LeaveWordsMapper extends BaseMapper<LeaveWords> {

    /**
     * 查询所有留言信息
     */
    List<LeaveWords> listLeaveWords();
}
