package online.superarilo.myblog.interceptor;

import com.alibaba.fastjson.JSONObject;
import online.superarilo.myblog.entity.SysLog;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.service.ISysLogService;
import online.superarilo.myblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Component
public class LogInterceptor implements HandlerInterceptor {

    private ISysLogService sysLogService;

    @Autowired
    public void setSysLogService(ISysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("开始拦截");

        SysLog sysLog = new SysLog();
        sysLog.setIp(request.getRemoteHost());
        sysLog.setUserAgent(request.getHeader("User-Agent"));
        sysLog.setMethod(request.getMethod());
        sysLog.setUrl(request.getRequestURL().toString());
        sysLog.setVisitTime(new Date());
        String token = request.getHeader("token");
        if(StringUtils.hasLength(token)) {
            UserInformation userInformation = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
            sysLog.setUid(userInformation.getUid());
            sysLog.setUsername(userInformation.getUsername());
        }
        sysLogService.save(sysLog);
        return true;
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
