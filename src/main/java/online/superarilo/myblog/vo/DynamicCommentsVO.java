package online.superarilo.myblog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import online.superarilo.myblog.entity.DynamicComments;

import java.util.List;

/**
 * 评论实体类
 * @author rong
 */

@Data
public class DynamicCommentsVO extends DynamicComments {

    /**
     * 子评论集合
     */
    @TableField(exist = false)
    private List<DynamicCommentsVO> children;

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String username;
}
