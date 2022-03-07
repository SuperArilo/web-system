package online.superarilo.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-02-23
 */
@Data
@TableName("inform")
public class Inform implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 接受通知者的uid
     */
    private Long receiver;

    /**
     * 发起通知者的uid, 如果为空 则表示系统通知
     */
    private Long notifier;

    /**
     * 标题
     */
    private String title;


    /**
     * 通知内容
     */
    private String content;

    /**
     * 事件源
     */
    private Long eventId;

    /**
     * 是否已读
     */
    @TableField("is_read")
    private Integer read;

    /**
     * 是否是系统通知
     */
    @TableField("is_sys")
    private Integer sys;

    /**
     * 时间
     */
    private Date createTime;
}
