package online.superarilo.myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;


/**
 * redis tools
 */
@Component
public class RedisUtil {

    public static final String VERIFY_CODE_REDIS_KEY_SUFFIX = "VERIFY_CODE_REDIS_KEY";

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> rt) {
        redisTemplate = rt;
    }

    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public static boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
