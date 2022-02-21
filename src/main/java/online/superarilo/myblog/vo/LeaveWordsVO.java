package online.superarilo.myblog.vo;

import lombok.Data;
import online.superarilo.myblog.entity.LeaveWords;
import online.superarilo.myblog.entity.UserInformation;

@Data
public class LeaveWordsVO extends LeaveWords {

    private UserInformation userInformation;

    private String classColor;

    private String className;
}
