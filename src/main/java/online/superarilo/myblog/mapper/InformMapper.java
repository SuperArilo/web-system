package online.superarilo.myblog.mapper;

import online.superarilo.myblog.entity.Inform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.superarilo.myblog.vo.InformVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-23
 */
public interface InformMapper extends BaseMapper<Inform> {


    /**
     * 查询通知内容
     * @param receiver 接收通知的人
     * @return 集合
     */
    List<InformVO> listInformByReceiver(Long receiver);

}
