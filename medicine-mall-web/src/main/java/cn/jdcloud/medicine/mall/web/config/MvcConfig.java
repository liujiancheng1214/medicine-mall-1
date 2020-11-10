package cn.jdcloud.medicine.mall.web.config;

import cn.jdcloud.framework.core.common.BaseGsonFactory;
import cn.jdcloud.framework.core.common.SpringContextUtil;
import com.google.gson.Gson;
import cn.jdcloud.medicine.mall.web.interceptor.MessageResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${web.api.url}")
    private String apiUrl;

    @Value("${web.upload.url}")
    private String uploadUrl;

    @Value("${web.base.url}")
    private String baseUrl;

    @Value("${web.static.url}")
    private String staticUrl;

    @Value("${web.version}")
    private String version;

    @Value("${web.platform.name}")
    private String platformName;

    @Value("${web.platform.logo}")
    private String platformLogo;


    @Autowired
    ThymeleafViewResolver viewResolver;



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
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        slr.setCookieMaxAge(3600);
        slr.setCookieName("Language");
        return slr;
    }

    /**
     * 注入拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new MessageResourceInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //加载全局变量
        if(viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            vars.put("apiUrl", apiUrl);
            vars.put("uploadUrl", uploadUrl);
            vars.put("baseUrl", baseUrl);
            vars.put("staticUrl", staticUrl);
            vars.put("version", version);
            Platform pf = new Platform();
            pf.setName(platformName);
            pf.setLogo(platformLogo);
            vars.put("platform", pf);


            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("GUARD_ID",12);
            map.put("SCHOOL_ADMIN",7);
            map.put("SUPER_ADMIN",8);
            map.put("HEART",10);
            vars.put("roleMaps",map);

            viewResolver.setStaticVariables(vars);
        }

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
       
        registry.addResourceHandler("/swagger/**")
        .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/swagger-resources/**")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }



}