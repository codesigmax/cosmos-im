package com.qfleaf.cosmosimserver.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Cosmos-IM API 文档")  // 文档标题
                                .version("alpha v0.1")               // 版本号
                                .description("Cosmos-IM 是基于SpringBoo3的即时通讯系统，当前为alpha版本，功能完善中。") // 描述
                                .contact(
                                        new Contact()        // 联系人信息
                                                .name("搴芳")
                                                .url("https://codesigmax.github.io")
                                )
                                .license(
                                        new License()        // 开源协议
                                                .name("Apache 2.0")
                                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                                )
                );
    }
}
