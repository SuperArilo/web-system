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
 * @since 2022-02-18
 */
@Data
@TableName("dynamic_comment")
public class DynamicComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 回复动态id
     */
    private Long dynamicId;

    /**
     * 回复者id
     */
    private Long replyId;

    /**
     * 被回复人id
     */
    private Long byReplyId;

    /**
     * 回复时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date replyTime;

    /**
     * 回复内容
     */
    private String replyContent;

}
