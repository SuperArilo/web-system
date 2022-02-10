package online.superarilo.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import online.superarilo.myblog.entity.SysDictionary;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.mapper.SysDictionaryMapper;
import online.superarilo.myblog.service.ISysDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.SysDictionaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-10
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {

    private SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    public void setSysDictionaryMapper(SysDictionaryMapper sysDictionaryMapper) {
        this.sysDictionaryMapper = sysDictionaryMapper;
    }

    @Override
    public Result<List<SysDictionaryVO>> listDictionary() {
        return new Result<>(true, HttpStatus.OK, "查询成功", sysDictionaryMapper.listSysDictionary(0));
    }

    @Override
    public Result<String> saveDictionary(SysDictionary sysDictionary, HttpServletRequest request) {
        if(!StringUtils.hasLength(sysDictionary.getDictKey())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "字典编码不能为空", null);
        }
        if(!StringUtils.hasLength(sysDictionary.getDictValue())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "字典值不能为空", null);
        }
        if(!StringUtils.hasLength(sysDictionary.getDictType())) {
            return new Result<>(false, HttpStatus.BAD_REQUEST, "字典类型不能为空", null);
        }
        if(Objects.isNull(sysDictionary.getDictParentId())) {
            sysDictionary.setDictParentId(0L);
        }
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "invalid token", null);
        }
        UserInformation userInformation = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        if(Objects.isNull(userInformation)) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED, "登录失效，请重新登录", null);
        }

        SysDictionary dictionary = new SysDictionary();
        dictionary.setDictDescribe(sysDictionary.getDictDescribe());
        dictionary.setDictKey(sysDictionary.getDictKey());
        dictionary.setDictEnable(true);
        dictionary.setDictSort(sysDictionary.getDictSort());
        dictionary.setDictType(sysDictionary.getDictType());
        dictionary.setDictValue(sysDictionary.getDictValue());
        dictionary.setCreateTime(new Date());
        dictionary.setDictParentId(sysDictionary.getDictParentId());
        dictionary.setIsDeleted(false);
        dictionary.setCreator(userInformation.getUid());
        this.save(dictionary);
        return new Result<>(true, HttpStatus.OK, "保存成功", "保存成功");
    }

    @Override
    public Result<String> removeDictionary(Long id) {
        return null;
    }

    @Override
    public Result<String> updateDictionary(SysDictionary sysDictionary) {
        return null;
    }
}
