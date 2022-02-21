package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-02-10
 */
@Data
@TableName("sys_dictionary")
public class SysDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 父ID
     */
    private Long dictParentId;

    /**
     * 字典编码
     */
    private String dictKey;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典描述
     */
    private String dictDescribe;

    /**
     * 是否启用(1 表示启用，0表示禁用)
     */
    private Boolean dictEnable;

    /**
     * 排序
     */
    private Long dictSort;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date updateTime;

    /**
     * 更新者
     */
    private Long updator;

    /**
     * 是否删除（1表示删除，0表示未删除）
     */
    @TableField("is_deleted")
    private Boolean deleted;
}
