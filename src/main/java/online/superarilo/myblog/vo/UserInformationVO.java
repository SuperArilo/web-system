package online.superarilo.myblog.vo;

import lombok.Data;
import online.superarilo.myblog.entity.UserInformation;

@Data
public class UserInformationVO extends UserInformation {

    private String classColor;

    private String className;
}
