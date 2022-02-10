package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("user_information")
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

    private String username;

    private String userpwd;

    private String email;

    @TableField("class")
    private String clazz;

    private String userhead;

    private String imagename;

    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.YYYY_MM_DD_HH_MM_SS_BIAS_PATTERN)
    private Date registertime;
}
