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
@Data
@TableName("reporting_system")
public class ReportingSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 举报用户id
     */
    private Long reportUid;

    /**
     * 被举报用户id
     */
    private Long beReportUid;

    /**
     * 被举报的动态id
     */
    private Long dynamicId;

    /**
     * 被处理意见
     */
    private String opinion;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否被处理（0未处理，1已处理）
     */
    private Boolean isHandle;

    /**
     * 举报时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;

    /**
     * 处理时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date updateTime;
}
