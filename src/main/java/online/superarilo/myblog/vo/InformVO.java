package online.superarilo.myblog.vo;

import lombok.Data;
import online.superarilo.myblog.entity.Inform;

@Data
public class InformVO extends Inform {

    /**
     * 通知者名称
     */
    private String receiverUsername;
}
