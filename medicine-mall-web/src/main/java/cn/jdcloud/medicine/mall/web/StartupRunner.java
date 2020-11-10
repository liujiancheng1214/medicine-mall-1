package cn.jdcloud.medicine.mall.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StartupRunner implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

  // @Value("${spring.cloud.config.profile}")
    //private String activeProfile;

//    @Value("${spring.datasource.druid.url}")
//    private String dbUrl;


    @Override
    public void run(String... args) throws Exception {
        //打印配置信息
    	
    	
        printConfig();
        
       
    }

    private void printConfig(){
        logger.info("=====================配置信息===================");
       // logger.info("spring.cloud.config.profile="+ activeProfile);
//        logger.info("spring.datasource.druid.url="+ dbUrl);
        logger.info("=====================配置信息===================");
    }

}
