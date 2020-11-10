package cn.jdcloud.medicine.mall.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.TimeZone;



@SpringBootApplication
@MapperScan(basePackages = "cn.jdcloud.medicine.mall.dao")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
@RestController
@EnableRedisHttpSession
public class ApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		Locale.setDefault(Locale.CHINA);
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		SpringApplication.run(ApiApplication.class, args);
	}

	@RequestMapping("/")
	public String welcome(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		return "medicine-mall-api is started successful:" + sessionId;
	}
}
