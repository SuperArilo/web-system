package online.superarilo.myblog.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.utils.DateUtil;

import java.util.Date;

@Data
public class TagsDTO extends Tags {

    /**
     * 开始时间
     * 时间格式 默认 yyyy/MM/dd HH:mm:ss
     */
    @TableField(exist = false)
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date startTime;

    /**
     * 结束时间
     * 时间格式 默认 yyyy/MM/dd HH:mm:ss
     */
    @TableField(exist = false)
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date endTime;


}
