package online.superarilo.myblog.vo;

import lombok.Data;
import online.superarilo.myblog.entity.DynamicComment;
import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.entity.UserInformation;

import java.util.Map;

/**
 * 评论实体类
 * @author rong
 */

@Data
public class DynamicCommentVO extends DynamicComment {

    /**
     * 回复者
     */
    private Map<String, Object> replyUser;

    /**
     * 被回复者
     */
    private Map<String, Object> byReplyUser;
}
