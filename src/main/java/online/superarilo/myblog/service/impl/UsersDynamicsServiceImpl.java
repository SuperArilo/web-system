package online.superarilo.myblog.service.impl;

import online.superarilo.myblog.entity.DynamicTagsRelations;
import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.mapper.UsersDynamicsMapper;
import online.superarilo.myblog.service.IDynamicTagsRelationsService;
import online.superarilo.myblog.service.ITagsService;
import online.superarilo.myblog.service.IUsersDynamicsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.UsersDynamicsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Service
public class UsersDynamicsServiceImpl extends ServiceImpl<UsersDynamicsMapper, UsersDynamics> implements IUsersDynamicsService {

    @Autowired
    private ITagsService tagsService;

    @Autowired
    private IDynamicTagsRelationsService dynamicTagsRelationsService;


    @Override
    public List<UsersDynamicsVO> listUserDynamics(Map<String, Object> queryParams) {
        return this.baseMapper.listUserDynamics(queryParams);
    }

    @Override
    @Transactional
    public Result<String> saveUserDynamic(UsersDynamicsVO usersDynamicsVO) {
        Result<String> before = this.before(usersDynamicsVO);
        if(before != null) {
            return before;
        }

        // 没选择标签,初始化集合
        if(usersDynamicsVO.getAlreadyExistedTagIds() == null) {
            usersDynamicsVO.setAlreadyExistedTagIds(new ArrayList<>());
        }

        // 定义变量
        List<DynamicTagsRelations> dynamicTagsRelationsEntities = new ArrayList<>();
        UsersDynamics usersDynamics = new UsersDynamics();

        // 保存新标签
        if (usersDynamicsVO.getTags() != null && usersDynamicsVO.getTags().size() > 0) {
            tagsService.saveBatch(usersDynamicsVO.getTags());
            Iterator<Tags> tagsEntityIterator = usersDynamicsVO.getTags().iterator();
            if (tagsEntityIterator.hasNext()) {
                usersDynamicsVO.getAlreadyExistedTagIds().add(tagsEntityIterator.next().getId()); // 将新添加的标签id加入动态标签组中
            }
        }

        // 保存动态
        usersDynamics.setDynamicTitle(usersDynamicsVO.getDynamicTitle());
        usersDynamics.setDynamicDescribe(usersDynamicsVO.getDynamicDescribe());
        usersDynamics.setDynamicContent(usersDynamicsVO.getDynamicContent());
        usersDynamics.setUid(usersDynamicsVO.getUid());
        usersDynamics.setCreateTime(new Date());
        this.save(usersDynamics);

        // 保存动态与标签的关系
        for (Integer tagId: usersDynamicsVO.getAlreadyExistedTagIds()) {
            DynamicTagsRelations dynamicTagsRelationsEntity = new DynamicTagsRelations();
            dynamicTagsRelationsEntity.setDynamicId(usersDynamics.getId());
            dynamicTagsRelationsEntity.setTagId(tagId);
            dynamicTagsRelationsEntity.setCreateTime(new Date());
            dynamicTagsRelationsEntities.add(dynamicTagsRelationsEntity);
        }
        dynamicTagsRelationsService.saveBatch(dynamicTagsRelationsEntities);

        return new Result<>(true, HttpStatus.OK, "success", "发布成功");
    }


    private Result<String> before(UsersDynamicsVO usersDynamicsVO) {
        if(usersDynamicsVO == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "参数不正确或参数不完整");
        }
        if(usersDynamicsVO.getUid() == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "用户信息不能为空");
        }
        if(!StringUtils.hasLength(usersDynamicsVO.getDynamicTitle())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "标题不能为空");
        }
        if(!StringUtils.hasLength(usersDynamicsVO.getDynamicDescribe())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "简介不能为空");
        }
        if(!StringUtils.hasLength(usersDynamicsVO.getDynamicContent())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "动态内容不能为空");
        }
        if(usersDynamicsVO.getTags() == null && usersDynamicsVO.getAlreadyExistedTagIds() == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "至少添加一个标签");
        }else {
            if(usersDynamicsVO.getTags() == null) {
                if(usersDynamicsVO.getAlreadyExistedTagIds().size() == 0) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "至少添加一个标签");
                }else if(usersDynamicsVO.getAlreadyExistedTagIds().size() > 3) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "最多添加三个标签");
                }
            } else if(usersDynamicsVO.getAlreadyExistedTagIds() == null) {
                if(usersDynamicsVO.getTags().size() == 0) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "至少添加一个标签");
                }else if(usersDynamicsVO.getTags().size() > 3) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "最多添加三个标签");
                }
            } else {
                if(usersDynamicsVO.getTags().size() + usersDynamicsVO.getAlreadyExistedTagIds().size() == 0) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "至少添加一个标签");
                }else if(usersDynamicsVO.getTags().size() + usersDynamicsVO.getAlreadyExistedTagIds().size() > 3) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "error", "最多添加三个标签");
                }
            }
        }
        return null;
    }
}
