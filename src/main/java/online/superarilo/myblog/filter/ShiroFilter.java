package online.superarilo.myblog.filter;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import online.superarilo.myblog.constant.RedisConstant;
import online.superarilo.myblog.entity.UserInformation;
import online.superarilo.myblog.realm.UserToken;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Service
public class ShiroFilter extends FormAuthenticationFilter {

    private HttpStatus errorCode;
    private String errorMsg;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new UserToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(token)) {
            this.errorCode = HttpStatus.UNAUTHORIZED;
            this.errorMsg = "invalid token";
            return false;
        }
        UserInformation userInfo;
        try {
            userInfo = JSONObject.parseObject(String.valueOf(RedisUtil.get(token)), UserInformation.class);
        } catch (Exception e) {
            this.errorCode = HttpStatus.UNAUTHORIZED;
            this.errorMsg = "token失效";
            return false;
        }
        if (userInfo == null) {
            this.errorCode = HttpStatus.BAD_REQUEST;
            this.errorMsg = "登录失败";
            return false;
        }
        //刷新超时时间
        RedisUtil.expire(token, RedisConstant.REDIS_LOGIN_EXPIRE); //30分钟过期
        UserToken userToken = new UserToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(userToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(new Result<>(false, this.errorCode, this.errorMsg, null)));
        return false;
    }

//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setContentType("application/json;charset=utf-8");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
//        try {
//            //处理登录失败的异常
//            Throwable throwable = e.getCause() == null ? e : e.getCause();
////            R r = R.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());
//            httpResponse.getWriter().print(JSONObject.toJSONString(new Result<Object>(false, HttpStatus.BAD_REQUEST, throwable.getMessage(), null)));
//        } catch (IOException e1) {
//
//        }
//
//        return false;
//    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }

}
