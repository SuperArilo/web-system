package online.superarilo.myblog.advice;

import com.alibaba.fastjson.JSONObject;
import online.superarilo.myblog.annotation.Log;
import online.superarilo.myblog.entity.SysLog;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.ISysLogService;
import online.superarilo.myblog.utils.HttpRequestContextUtil;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.SpringBeanUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAnnotationAdvice {

    private static final String USER_AGENT = "User-agent";

    private static final String TOKEN = "token";

    @Pointcut("execution(public * online.superarilo.myblog.controller.*.*(..))")
    public void declareJointPointExpression() {
    }

    @Before("declareJointPointExpression()")
    public void beforeMethod(JoinPoint joinPoint) {
        Method method = ((MethodSignature) (joinPoint.getSignature())).getMethod();
        HttpServletRequest request = request = HttpRequestContextUtil.getRequest();

        if(method.isAnnotationPresent(Log.class)) {
            log(request, method);
        }
    }


    public void log(HttpServletRequest request, Method method) {
        SysLog sysLog = new SysLog();
        sysLog.setVisitServiceMethod(method.getName());
        sysLog.setIp(request.getRemoteHost());
        sysLog.setUserAgent(request.getHeader(USER_AGENT));
        sysLog.setMethod(request.getMethod());
        sysLog.setUrl(request.getRequestURL().toString());
        sysLog.setVisitTime(new Date());
        String token = request.getHeader(TOKEN);
        if(StringUtils.hasLength(token)) {
            UserInformation userInformation = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
            sysLog.setUid(userInformation.getUid());
            sysLog.setUsername(userInformation.getUsername());
        }
        ISysLogService logService = SpringBeanUtil.getBean(ISysLogService.class);
        logService.save(sysLog);
    }

}
