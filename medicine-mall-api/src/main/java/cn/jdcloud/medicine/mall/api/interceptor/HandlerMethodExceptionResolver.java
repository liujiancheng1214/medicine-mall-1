package cn.jdcloud.medicine.mall.api.interceptor;
import com.google.gson.Gson;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.core.vo.ApiResult;
import org.apache.ibatis.exceptions.IbatisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * 控制层异常处理类
 * User: qun.xu
 * Date: 2019-01-09
 * Time: 下午6:35
 */
@Component("handlerMethodExceptionResolver")
public class HandlerMethodExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(HandlerMethodExceptionResolver.class);

    @Resource(name="gson")
    private Gson gson;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        // 异常处理
//        ex.printStackTrace();
        ApiException cex = null;
        //抛到前端的异常类
        if (ex instanceof ApiException) {
            cex = (ApiException) ex;
            //sql语句错误异常
        }else if(ex instanceof SQLException ||
                ex instanceof DataAccessException ||
                ex instanceof IbatisException){
            ex.printStackTrace();
            cex = new ApiException(SysCodeEnums.SYSTEM_DATA_ERROR);
        }
        if(cex!=null){
            try {
                // 默认跳转
                ModelAndView mv = new ModelAndView();
                response.setStatus(HttpStatus.OK.value()); //设置状态码
                response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
                response.setCharacterEncoding("UTF-8"); //避免乱码
                ApiResult rv = ApiResult.error(cex);
                String rtnJson = gson.toJson(rv);
                response.getWriter().write(rtnJson);
                return mv;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
