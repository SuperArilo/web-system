package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.SysDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.superarilo.myblog.vo.SysDictionaryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-10
 */
@Mapper
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    List<SysDictionaryVO> listSysDictionary(@Param("parentId") Integer parentId);
}
