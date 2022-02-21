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
 * @since 2022-02-21
 */
@Data
@TableName("leave_words")
public class LeaveWords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 用户uid
     */
    private Long uid;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;


}
