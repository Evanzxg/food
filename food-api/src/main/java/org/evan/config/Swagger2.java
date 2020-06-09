package org.evan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/23 0023
 * Time: 0:11
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 配置swagger2核心配置 docket
     * @return
     */
    @Bean
    public Docket createRestApi(){
        //指定api类型为swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //指定需要扫描的controller包
                .select().apis(RequestHandlerSelectors.basePackage("org.evan.controller"))
                //所有controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Food吃货 电商平台API")
                .contact(new Contact("evan",
                        "https://www.cnblogs.com/zxgwork/",
                        "xiaogang.evan@gmail.com"))
                .description("Evan个人练习项目提供的api文档")
                .version("1.0.1")
                .termsOfServiceUrl("www.domore.com")
                .build();
    }

}
