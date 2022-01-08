package online.superarilo.myblog.service.impl;

import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.mapper.TagsMapper;
import online.superarilo.myblog.service.ITagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

}
