package cn.jdcloud.medicine.mall.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author qun.xu
 * @desc 配置跨域支持
 */
@Configuration
public class CorsConfig {

    @Value("${cms.web.domain}")
    private String webDomain;

    private CorsConfiguration buildConfig() {
        CorsConfiguration conf = new CorsConfiguration();
        conf.addAllowedHeader("*");
        conf.addAllowedMethod("*");
        conf.addAllowedOrigin("*");
        conf.setAllowCredentials(true);
        conf.setMaxAge(3600L);
        conf.addExposedHeader("set-cookie");
        conf.addExposedHeader("access-control-allow-headers");
        conf.addExposedHeader("access-control-allow-methods");
        conf.addExposedHeader("access-control-allow-origin");
        conf.addExposedHeader("access-control-max-age");
        conf.addExposedHeader("X-Frame-Options");
        return conf;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration confApp = buildConfig();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", confApp); //  对app接口配置跨域设置
        CorsConfiguration confCms = buildConfig();
        confCms.addAllowedOrigin(webDomain);
        source.registerCorsConfiguration("/cms/**",confCms);//  对cmsWeb配置跨域设置
        return new CorsFilter(source);
    }
}