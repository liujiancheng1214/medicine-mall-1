package cn.jdcloud.medicine.mall.web;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableRedisHttpSession
@MapperScan(basePackages = "cn.jdcloud.medicine.mall.dao")
@RestController
@EnableSwagger2
public class WebApplication {
// redis-server redis.windows.conf
	// 093738177498
	@RequestMapping("/web")
	public String welcome(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		return "web is started successful:" + sessionId;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
