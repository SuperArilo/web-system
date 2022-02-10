package online.superarilo.myblog.redis;

import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.entity.UsersDynamics;
import online.superarilo.myblog.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Set;

@SpringBootTest
public class RedisTest {


    @Test
    public void test1() {
        UserInformation user = new UserInformation();
        user.setUid(1L);
        user.setUsername("1233");
        user.setUserpwd("1234");
        user.setUserhead("121");
        user.setEmail("1222");
        user.setRegistertime(new Date());
        try {
            RedisUtil.setObject("user", user, UserInformation.class);
            UserInformation user1 = RedisUtil.getObject("user", UserInformation.class);
            System.out.println(user1);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }


    }
}
