package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private Integer clazz;

    private String className;

    private String classColor;

    private String userhead;

    private String imagename;

    private Date registertime;
}
