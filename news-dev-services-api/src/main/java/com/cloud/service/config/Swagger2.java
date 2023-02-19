package com.cloud.service.config;


import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    //    http://localhost:8088/swagger-ui.html     原路径
    //    http://localhost:8088/doc.html            新路径

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> userPredicate = RequestHandlerSelectors.basePackage("com.cloud");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(userPredicate)
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档页标题
                .title("新闻·自媒体接口api")
                // 联系人信息
                .contact(new Contact("余小帅",
                        "https://www.cloudnews.com",
                        "991416851@qq.com"))
                // 详细信息
                .description("新闻·自媒体平台提供的api文档")
                // 文档版本号
                .version("1.0.1")
                // 网站地址
                .termsOfServiceUrl("https://www.cloudnews.com")
                .build();
    }
}




