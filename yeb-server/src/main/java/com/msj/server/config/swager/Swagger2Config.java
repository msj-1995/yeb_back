package com.msj.server.config.swager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置类
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // api相关信息
                .apiInfo(apiInfo())
                .select()
                // 指定扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.msj.server.controller"))
                // 配置扫描任何路径
                .paths(PathSelectors.any())
                .build()
                .securityContexts()
                .securitySchemes();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("云e办接口文档")
                .description("云e办接口文档")
                .contact(new Contact("msj", "http://localhost:8001/doc.html","xxxx@xxx.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        // 设置请求头信息
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        result.add(apiKey);
        return result;
    }


}
