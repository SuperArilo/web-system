package online.superarilo.myblog.vo;

import lombok.Data;
import online.superarilo.myblog.entity.LeaveWords;

@Data
public class LeaveWordsVO extends LeaveWords {

    private UserInformationVO user;

    private String classColor;

    private String className;
}
