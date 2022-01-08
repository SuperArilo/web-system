package online.superarilo.myblog.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisFilter {
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    public  Boolean checkFilter(String baseName,String banName,String ip,Integer number,Integer time){
        Object objectBan = redisTemplate.opsForHash().get(banName + ip,ip);
        Object object = redisTemplate.opsForHash().get(baseName + ip,ip);
        if (objectBan == null){
            if (object == null){
                redisTemplate.opsForHash().put(baseName + ip,ip,0);
                redisTemplate.expire(baseName + ip,time, TimeUnit.HOURS);
                return true;
            } else {
                if ((Integer)object >= number){
                    redisTemplate.opsForHash().put(banName + ip,ip,1);
                    redisTemplate.expire(banName + ip,time,TimeUnit.HOURS);
                    Set<String> keys = redisTemplate.keys(baseName + ip);
                    if (keys != null){
                        redisTemplate.delete(keys);
                    }
                    return false;
                } else {
                    redisTemplate.opsForHash().increment(baseName + ip,ip,1);
                    return true;
                }
            }
        } else {
            return false;
        }
    }
    public Boolean checkUserToken(String userEmail, String token, String userPwd){
        if (JwtUtils.verify(token,userEmail,userPwd)){
            if (Boolean.TRUE.equals(redisTemplate.hasKey(userEmail))){
                return token.equals(redisTemplate.opsForValue().get(userEmail));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public void setToRedis(String key,Object value,Integer time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }
    public Boolean checkHaveKey(String key){
        return redisTemplate.hasKey(key);
    }
    public Object getInRedisObject(String key){
        return redisTemplate.opsForValue().get(key);
    }
    public void delInRedis(String keyName){
        Set<String> keys = redisTemplate.keys(keyName);
        assert keys != null;
        redisTemplate.delete(keys);
    }
}
