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
@TableName("users_dynamics")
public class UsersDynamics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 动态标题
     */
    private String dynamicTitle;

    /**
     * 动态简介/描述
     */
    private String dynamicDescribe;

    /**
     * 主体内容
     */
    private String dynamicContent;

    /**
     * 浏览量
     */
    private Long dynamicPageView;

    /**
     * 是否被举报(0未举报，1被举报)
     */
    private Boolean isReporting;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtil.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date createTime;
}
