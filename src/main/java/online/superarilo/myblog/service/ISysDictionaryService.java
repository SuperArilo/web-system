package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.SysDictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.SysDictionaryVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-10
 */
public interface ISysDictionaryService extends IService<SysDictionary> {


    /**
     * 查询集合
     * @return 结果
     */
    Result<List<SysDictionaryVO>> listDictionary();

    /**
     * 保存
     * @param sysDictionary 字典对象
     * @return 结果
     */
    Result<String> saveDictionary(SysDictionary sysDictionary, HttpServletRequest request);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Result<String> removeDictionary(Long id);

    /**
     * 保存
     * @param sysDictionary 字典对象
     * @return 结果
     */
    Result<String> updateDictionary(SysDictionary sysDictionary);

}
