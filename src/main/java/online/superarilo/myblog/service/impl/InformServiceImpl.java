package online.superarilo.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import online.superarilo.myblog.entity.Inform;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.InformMapper;
import online.superarilo.myblog.service.IInformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.service.IUserInformationService;
import online.superarilo.myblog.utils.JsonResult;
import online.superarilo.myblog.utils.PageUtil;
import online.superarilo.myblog.vo.InformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-23
 */
@Service
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform> implements IInformService {


    private IUserInformationService userInformationService;

    @Autowired
    public void setUserInformationService(IUserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @Override
    public JsonResult listInformByReceiver(Integer pageNumber, Long receiver) {
        final Integer defaultPageSize = 6;
        if(Objects.isNull(pageNumber) || pageNumber < 0) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "分页参数不正确");
        }
        if(Objects.isNull(receiver)) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "没有接收通知的人");
        }
        PageHelper.startPage(pageNumber, defaultPageSize);
        List<InformVO> informVOList = this.baseMapper.listInformByReceiver(receiver);
        PageInfo<InformVO> pageInfo = new PageInfo<>(informVOList);
        return JsonResult.OK(new PageUtil(pageInfo));
    }

    @Override
    public JsonResult addInform(InformVO informVO) {
        UserInformation one = userInformationService.getOne(new QueryWrapper<UserInformation>().lambda().eq(UserInformation::getUid, informVO.getReceiver()));
        if(Objects.isNull(one)) {
            return JsonResult.ERROR(HttpStatus.BAD_REQUEST.value(), "接收通知对象不存在");
        }
        Inform inform = new Inform();
        inform.setReceiver(informVO.getReceiver());
        if(Integer.valueOf(0).equals(inform.getSys())) {
            inform.setNotifier(informVO.getNotifier());
        }
        inform.setContent(informVO.getContent());
        inform.setEventId(informVO.getEventId());
        inform.setCreateTime(informVO.getCreateTime());
        inform.setRead(informVO.getRead());
        inform.setSys(informVO.getSys());
        this.save(inform);
        return JsonResult.OK("通知成功");
    }
}
