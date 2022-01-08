package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@Data
@TableName("dynamic_comments")
public class DynamicComments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论父id(最多两级)
     */
    private Integer commentParentId;

    /**
     * 发布评论的动态的id
     */
    private Integer dynamicId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 发布评论的用户id
     */
    private Integer uid;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
