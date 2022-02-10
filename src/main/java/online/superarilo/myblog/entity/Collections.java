package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import online.superarilo.myblog.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("collections")
public class Collections implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 收藏的用户id
     */
    private Long uid;

    /**
     * 收藏的动态id
     */
    private Long dynamicId;

    /**
     * 收藏时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;

}
