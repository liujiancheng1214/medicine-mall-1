package cn.jdcloud.medicine.mall.api;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//@Configuration
//@EnableSwagger2
public class Swagger2Config {
	private boolean swaggerEnabled=true;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.enable(swaggerEnabled)
                .apiInfo(apiInfo())
                .groupName(Docket.DEFAULT_GROUP_NAME)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.jdcloud.medicine.mall.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("医药商城-API接口")
                .description("江西京电云科技有限公司")
                .contact(new Contact("青云团队","",""))
                .version("1.0.0")
                .build();
    }
}
