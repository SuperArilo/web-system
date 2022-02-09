package online.superarilo.myblog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.entity.UsersDynamics;

import java.util.List;

@Data
public class UsersDynamicsVO extends UsersDynamics {

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String username;

    /**
     * 用户头像
     */
    @TableField(exist = false)
    private String userHead;

    /**
     * 用户类别
     */
    @TableField(exist = false)
    private Integer clazz;

    /**
     * 评论数量
     */
    private Integer commentNum;

    /**
     * 标签集合
     */
    @TableField(exist = false)
    private List<Tags> tags;

    /**
     * 选中的标签id集合
     */
    @TableField(exist = false)
    private List<Long> alreadyExistedTagIds;
}
