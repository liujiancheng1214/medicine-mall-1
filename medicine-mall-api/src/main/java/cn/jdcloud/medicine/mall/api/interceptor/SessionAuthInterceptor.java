package cn.jdcloud.medicine.mall.api.interceptor;

import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.annotation.PermissionRequired;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.medicine.mall.client.code.AuthCode;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

@Component
@Slf4j
public class SessionAuthInterceptor implements HandlerInterceptor {

    /**
     * 用户权限验证（登录验证和权限验证）
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截方法注解LoginRequired（true）
        if(loginRequired(handler)){
            //判断用户Session是否存在
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            if(null==userSession){
                //抛出没有登录异常
                throw new ApiException(AuthCode.USER_NEEDLOGIN);
            }else{
                String[] permission = permissionRequired(handler);
                Set<String> permissionValue = userSession.getPermissionValue();
                //权限
                Boolean isok = permissionValidate(permission,permissionValue);
                if(isok == false){
                    throw new ApiException(AuthCode.USER_PERMISSIONDENTAIL);
                }
            }

        }else{
            return true;
        }
        return true;
    }

    private boolean permissionValidate(String[] permission, Set<String> permissionValue){
        Boolean isok = false;
        if(permission != null){
            for(String p : permission){
                if(permissionValue.contains(p)){
                    isok = true;
                    return isok;
                }
            }
            return isok;
        }else{
            isok = true;
        }
        return isok;
    }

    /**
     * 判断类和方法中的 //@PermissionRequired注解
     * 方法注解 > 类注解
     * @param handler
     * @return ""代表不拦截
     */
    private String[] permissionRequired(Object handler){
        if (!(handler instanceof HandlerMethod)) {
            return null;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method =handlerMethod.getMethod();
        //先找类注解 全局有效
        PermissionRequired clazzLr = method.getDeclaringClass().getAnnotation(PermissionRequired.class);
        PermissionRequired methodLr = handlerMethod.getMethodAnnotation(PermissionRequired.class);
        //以方法注解为最后生效
        PermissionRequired lr = null;
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
        if(lr == null || "".equals(lr.value())){
            return null;
        }else{
            return lr.value();
        }

    }

    /**
     * 判断类和方法中的 @LoginRequired注解
     * 方法注解 > 类注解
     * @param handler
     * @return
     */
    private boolean loginRequired(Object handler){
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
        if(lr != null && lr.value()==false){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
