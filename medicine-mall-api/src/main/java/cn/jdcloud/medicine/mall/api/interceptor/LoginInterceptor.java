package cn.jdcloud.medicine.mall.api.interceptor;

import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.common.ErrorCode;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.api.biz.user.enums.UserCode;
import cn.jdcloud.medicine.mall.api.token.service.TokenService;
import cn.jdcloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 *
 * @author qun.xu
 * @desc   基础认证拦截器
 * @date 2018.04.03  HandlerInterceptorAdapter
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //不拦截方法注解LoginRequired（false）
        if(!loginRequired(handler)){
            return true;
        }
        //从请求头取出token 如果没有的话就是未登陆的状态 返回异常
        String accessToken = request.getHeader("accessToken");
        //从请求头取出userId 如果没有的话就是未登陆的状态 返回异常
        String uidStr = request.getHeader("userId");
        //从请求头取出userType
        String type = request.getHeader("userType");

        //如果userID是空
        if(StringUtils.isEmpty(uidStr) || !StringUtils.isNumeric(uidStr)) {
            log.warn("userId is null || not number =" + uidStr);
            dealError(UserCode.TOKEN_IS_INVALID, response);
        }

        //如果userType是空
        if(StringUtils.isEmpty(type) || !StringUtils.isNumeric(type)) {
            log.warn("userType is null");
            dealError(UserCode.TOKEN_IS_INVALID, response);
        }
        //TOKEN是否为空
        if (accessToken == null) {
            dealError(UserCode.TOKEN_IS_INVALID, response);
        }
        //判断token是否合法
        if(accessToken.length()!=32){
            dealError(UserCode.TOKEN_IS_WRONGFULNESS, response);
        }
        Integer uid = Integer.parseInt(uidStr);
        byte userType = Byte.parseByte(type);
       //如果请求头里的token和缓存里的token不一样的话 返回异常
        boolean accessTokenResult = tokenService.checkAccToken(uid, accessToken,userType);
        if(!accessTokenResult) {
            dealError(UserCode.TOKEN_IS_INVALID, response);
        }
        return true;
    }

    /**
     * 判断类和方法中的 @LoginRequired注解
     * 方法注解 > 类注解
     * @param handler
     * @return
     */
    public boolean loginRequired(Object handler){
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method =handlerMethod.getMethod();
        //先找类注解 全局有效
        LoginRequired clazzLr = method.getDeclaringClass().getAnnotation(LoginRequired.class);
        LoginRequired methodLr = handlerMethod.getMethodAnnotation(LoginRequired.class);
        //以方法注解为最后生效
        LoginRequired lr = null;
        if(clazzLr==null){
            lr = methodLr;
        }else{
            if(methodLr==null){
                lr = clazzLr;
            }else{
                lr = methodLr;
            }
        }
        //不需要登录
        if(lr!=null && lr.value()==false){
            return false;
        }else{
            return true;
        }

    }

    public void dealError(ErrorCode code, HttpServletResponse response)throws Exception{
        throw new ApiException(code);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
