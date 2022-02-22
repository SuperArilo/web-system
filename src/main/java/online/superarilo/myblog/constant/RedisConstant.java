package online.superarilo.myblog.constant;

import online.superarilo.myblog.utils.DateUtil;

/**
 * 定义 Redis key 过期时间常量
 */
public class RedisConstant {


    /**
     * 第一种登录过期时间 一天时间
     */
    public static final Long REDIS_LOGIN_EXPIRE = 24 * 60 * 60L;

}
