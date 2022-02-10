package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import online.superarilo.myblog.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Data
@TableName("dynamic_tags_relations")
public class DynamicTagsRelations implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 动态id
     */
    private Long dynamicId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;
}
