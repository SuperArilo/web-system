package online.superarilo.myblog.utils;

import online.superarilo.myblog.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;


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

    public static void expire(String key, int time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    // hash
    public static <T> void setObject(String key, T obj, Class<T> clazz) throws IllegalAccessException {
        Field[] declaredFields = clazz.getDeclaredFields();
        redisTemplate.multi();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            Object value = declaredField.get(obj);
            redisTemplate.opsForHash().put(key, name, value);
        }
        redisTemplate.exec();
    }

    public static <T> T getObject(String key, Class<T> clazz) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, InstantiationException {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        Set<Object> objects = entries.keySet();
        T instance = clazz.getConstructor().newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            // 查看类型是否是静态类型
            if(Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            if(objects.contains(declaredField.getName())) {
                Class<?> type = declaredField.getType();
                if (type.getName().equalsIgnoreCase("java.lang.Long")) {
                    declaredField.set(instance, Long.valueOf(String.valueOf(entries.get(declaredField.getName()))));
                    continue;
                }
                if(type.getName().equalsIgnoreCase("java.util.Date")) {
                    declaredField.set(instance, new Date(Long.parseLong(String.valueOf(entries.get(declaredField.getName())))));
                    continue;
                }
                declaredField.set(instance, entries.get(declaredField.getName()));
            }
        }
        return instance;
    }
}
