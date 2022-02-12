package online.superarilo.myblog.service;

import online.superarilo.myblog.dto.TagsDTO;
import online.superarilo.myblog.entity.Tags;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.TagsVO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
public interface ITagsService extends IService<Tags> {

//    List<TagsVO> listTags(TagsDTO tagsDTO);

     Result<List<Tags>> listTags();
}
