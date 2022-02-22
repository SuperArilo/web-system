package online.superarilo.myblog.date;

import online.superarilo.myblog.utils.DateUtil;
import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void test1() {
        long l = DateUtil.theRestOfTheDay();
        System.out.println(l);
    }
}
