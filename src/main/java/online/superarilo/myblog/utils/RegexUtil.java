package online.superarilo.myblog.utils;

public class RegexUtil {


    /**
     * 密码验证规则
     */
    public static final String ALPHANUMERIC_CHARACTERS_AND_SPECIAL_CHARACTERS = "(([0-9]+[a-zA-Z]+)|([a-zA-Z]+[0-9]+)|([@./*-+%$#]*)){6,16}";

    /**
     * 邮箱验证规则
     */
    public static final String MAIL_REGEX = "^\\w+@(\\w)+(\\.[a-zA-Z]+)+$";
}