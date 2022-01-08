package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private Integer id;

    private String username;

    private String userpwd;

    private String email;

    @TableField("class")
    private Integer clazz;

    private String userhead;

    private String imagename;

    private LocalDateTime registertime;
}
