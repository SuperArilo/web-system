package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.Inform;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.JsonResult;
import online.superarilo.myblog.vo.InformVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-23
 */
public interface IInformService extends IService<Inform> {


    /**
     * 根据接收者id查询通知
     * @param pageNumber 第几页
     * @param receiver 接收者id
     * @return 结果
     */
    JsonResult listInformByReceiver(Integer pageNumber, Long receiver);


    /**
     * 添加通知
     * @param informVO 通知
     * @return 结果
     */
    JsonResult addInform(InformVO informVO);
}
