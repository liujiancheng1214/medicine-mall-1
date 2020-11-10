package cn.jdcloud.medicine.mall.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.google.gson.Gson;

import cn.jdcloud.framework.core.common.BaseGsonFactory;
import cn.jdcloud.framework.core.common.SpringContextUtil;
import cn.jdcloud.medicine.mall.api.interceptor.LoginInterceptor;
import cn.jdcloud.medicine.mall.api.interceptor.SessionAuthInterceptor;

/**
 * @author qun.xu
 * @desc mvc config 添加拦截器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private SessionAuthInterceptor sessionAuthInterceptor;

    /**
     * Gson转换器替换默认json
     */
    @Bean("gson")
    public Gson gsonBean(){
        return BaseGsonFactory.createGson();
    }

    @Bean("springContextUtil")
    public SpringContextUtil springContextUtilBean(){
        return new SpringContextUtil();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * 注入拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration appReg = registry.addInterceptor(loginInterceptor);
        appReg.addPathPatterns("/app/**");

        InterceptorRegistration cmsReg = registry.addInterceptor(sessionAuthInterceptor);
        cmsReg.addPathPatterns("/cms/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations(
//                "classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//                "classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations(
//                "classpath:/META-INF/resources/webjars/");

//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}