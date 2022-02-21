package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import online.superarilo.myblog.utils.DateUtil;

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

/**
 * @since 2022-02-18
 * 该类已弃用 替代为 online.superarilo.myblog.entity.DynamicComment
 */
@Data
@TableName("dynamic_comments")
public class DynamicComments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 评论父id(最多两级)
     */
    private Long commentParentId;

    /**
     * 发布评论的动态的id
     */
    private Long dynamicId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 发布评论的用户id
     */
    private Long uid;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;
}
