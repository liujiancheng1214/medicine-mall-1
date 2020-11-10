package cn.jdcloud.medicine.mall.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {

        @Bean
        public Docket createAuthApi() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .apiInfo(apiInfo()).groupName("认证管理")
                 .select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.auth"))
                 .paths(PathSelectors.any()).build();
        }

        @Bean
        public Docket createChannelApi() {
         return new Docket(DocumentationType.SWAGGER_2).
                  apiInfo(apiInfo()).groupName("通道管理").
                  select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.channel"))
                 .paths(PathSelectors.any()).build();
        }

        @Bean
        public Docket createClientApi() {
         return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("客户管理").select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.client"))
                 .paths(PathSelectors.any()).build();
        }

        @Bean
        public Docket createFinanceApi() {
         return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).
                 groupName("财务管理").select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.finance"))
                 .paths(PathSelectors.any()).build();
        }

        @Bean
        public Docket createSignApi() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .apiInfo(apiInfo()).groupName("签名管理")
                 .select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.sign"))
                 .paths(PathSelectors.any()).build();
        }

       @Bean
       public Docket createTemplateApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).groupName("模板管理")
                .select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.template"))
                .paths(PathSelectors.any()).build();
       }

       @Bean
       public Docket createWordApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).groupName("敏感词管理")
                .select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.word"))
                .paths(PathSelectors.any()).build();
       }


       @Bean
       public Docket createToolsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).groupName("工具管理")
                .select().apis(RequestHandlerSelectors.basePackage("com.cs.sms.server.biz.tools"))
                .paths(PathSelectors.any()).build();
       }


       private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("创胜短信平台中控端-API文档")
                   .description("创胜短信平台中控端-API文档").termsOfServiceUrl("https://www.any163.cn")
                    .contact("609216251@qq.com").version("1.0")
                .build();
       }
}
