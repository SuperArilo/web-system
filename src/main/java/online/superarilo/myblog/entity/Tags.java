package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名称
     */
    private String tagContent;

    /**
     * 标签类型
     */
    private String tagType;

    /**
     * 创建时间
     * 时间格式 默认 yyyy/MM/dd HH:mm:ss
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;
}