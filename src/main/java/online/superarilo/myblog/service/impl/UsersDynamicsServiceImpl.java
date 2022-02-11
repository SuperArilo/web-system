package online.superarilo.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import online.superarilo.myblog.entity.DynamicTagsRelations;
import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.mapper.UsersDynamicsMapper;
import online.superarilo.myblog.service.IDynamicTagsRelationsService;
import online.superarilo.myblog.service.ITagsService;
import online.superarilo.myblog.service.IUsersDynamicsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.UsersDynamicsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public Result<UsersDynamicsVO> queryDynamicById(Long dynamicId) {
        if(dynamicId == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "参数有误", null);
        }
        UsersDynamics selDynamic = this.getOne(new QueryWrapper<UsersDynamics>()
                .lambda().eq(UsersDynamics::getId, dynamicId)
                .eq(UsersDynamics::getIsReporting, 0).last("limit 1"));
        if(Objects.isNull(selDynamic)) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "查询的动态不存在或已被举报", null);
        }

        UsersDynamicsVO usersDynamicsVO = this.baseMapper.queryDynamicById(dynamicId);

        return new Result<>(true, HttpStatus.OK, "查询成功", usersDynamicsVO);
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
            for (Tags tags : usersDynamicsVO.getTags()) {
                usersDynamicsVO.getAlreadyExistedTagIds().add(tags.getId()); // 将新添加的标签id加入动态标签组中
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
        for (Long tagId: usersDynamicsVO.getAlreadyExistedTagIds()) {
            DynamicTagsRelations dynamicTagsRelationsEntity = new DynamicTagsRelations();
            dynamicTagsRelationsEntity.setDynamicId(usersDynamics.getId());
            dynamicTagsRelationsEntity.setTagId(tagId);
            dynamicTagsRelationsEntity.setCreateTime(new Date());
            dynamicTagsRelationsEntities.add(dynamicTagsRelationsEntity);
        }
        dynamicTagsRelationsService.saveBatch(dynamicTagsRelationsEntities);

        return new Result<>(true, HttpStatus.OK, "发布成功", null);
    }

    @Override
    public Result<String> incrementDynamicPageView(Long dynamicId, HttpServletRequest request) {
        if(dynamicId == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "参数有误", null);
        }
        UsersDynamics selDynamic = this.getOne(new QueryWrapper<UsersDynamics>()
                .lambda().eq(UsersDynamics::getId, dynamicId)
                .eq(UsersDynamics::getIsReporting, 0).last("limit 1"));
        if(Objects.isNull(selDynamic)) {
            return new Result<>(false, HttpStatus.NOT_FOUND, "动态不存在或已被举报", null);
        }

        String sameIPRedisKey = dynamicId + ":" + request.getRemoteHost();
        if(RedisUtil.hasKey(sameIPRedisKey)) {
            return new Result<>(true, HttpStatus.OK, "30分钟内浏览相同动态算一次哦", null);
        }
        RedisUtil.set(sameIPRedisKey, sameIPRedisKey, 60 * 30, TimeUnit.SECONDS);
//        RedisUtil.set(sameIPRedisKey, sameIPRedisKey ,10, TimeUnit.SECONDS); // 测试10秒

        this.update(new UpdateWrapper<UsersDynamics>()
                .lambda()
                .set(UsersDynamics::getDynamicPageView, selDynamic.getDynamicPageView() + 1)
                .eq(UsersDynamics::getId, selDynamic.getId()));
        return new Result<>(true, HttpStatus.OK, dynamicId + ": 浏览数量加一", null);
    }


    private Result<String> before(UsersDynamicsVO usersDynamicsVO) {
        if(usersDynamicsVO == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST,"参数不正确或参数不完整", null);
        }
        if(usersDynamicsVO.getUid() == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST,"用户信息不能为空", null);
        }
        if(!StringUtils.hasLength(usersDynamicsVO.getDynamicTitle())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "标题不能为空", null);
        }
        if(!StringUtils.hasLength(usersDynamicsVO.getDynamicDescribe())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "简介不能为空", null);
        }
        if(!StringUtils.hasLength(usersDynamicsVO.getDynamicContent())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST,"动态内容不能为空", null);
        }
        if(usersDynamicsVO.getTags() == null && usersDynamicsVO.getAlreadyExistedTagIds() == null) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "至少添加一个标签", null);
        }else {
            if(usersDynamicsVO.getTags() == null) {
                if(usersDynamicsVO.getAlreadyExistedTagIds().size() == 0) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "至少添加一个标签", null);
                }else if(usersDynamicsVO.getAlreadyExistedTagIds().size() > 3) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST,"最多添加三个标签", null);
                }
            } else if(usersDynamicsVO.getAlreadyExistedTagIds() == null) {
                if(usersDynamicsVO.getTags().size() == 0) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "至少添加一个标签", null);
                }else if(usersDynamicsVO.getTags().size() > 3) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST,"最多添加三个标签", null);
                }
            } else {
                if(usersDynamicsVO.getTags().size() + usersDynamicsVO.getAlreadyExistedTagIds().size() == 0) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "至少添加一个标签", null);
                }else if(usersDynamicsVO.getTags().size() + usersDynamicsVO.getAlreadyExistedTagIds().size() > 3) {
                    return new Result<>(false, HttpStatus.BAD_REQUEST, "最多添加三个标签", null);
                }
            }
        }
        return null;
    }
}
