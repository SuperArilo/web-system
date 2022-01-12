package online.superarilo.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.superarilo.myblog.dto.TagsDTO;
import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.mapper.TagsMapper;
import online.superarilo.myblog.service.ITagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.superarilo.myblog.vo.TagsVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Override
    public List<TagsVO> listTags(TagsDTO tagsDTO) {
        LambdaQueryWrapper<Tags> lambdaQueryWrapper = new QueryWrapper<Tags>().lambda();
        if(tagsDTO != null && StringUtils.hasLength(tagsDTO.getTagContent())) {
            lambdaQueryWrapper.like(Tags::getTagContent, tagsDTO.getTagContent());
        }
        if(tagsDTO != null && tagsDTO.getStartTime() != null) {
            lambdaQueryWrapper.ge(Tags::getCreateTime, tagsDTO.getStartTime());
        }
        if(tagsDTO != null && tagsDTO.getEndTime() != null) {
            lambdaQueryWrapper.le(Tags::getCreateTime, tagsDTO.getEndTime());
        }
        List<Tags> list = this.list(lambdaQueryWrapper);
        return null;
    }
}
