package cn.jdcloud.medicine.mall.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StartupRunner implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(
            StartupRunner.class);

    @Value("${spring.datasource.druid.url}")
    private String dbUrl;
    @Value("${spring.redis.host}")
    private String redisHost;

    @Override
    public void run(String... args) throws Exception {
        //打印配置信息
        printConfig();
    }

    private void printConfig(){
        logger.info("=====================配置信息===================");
        logger.info("数据库连接："+ dbUrl);
        logger.info("Redis主机:"+this.redisHost);
        logger.info("=====================配置信息===================");
    }
}
