package com.bbps.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("BBPS-IncomingRequest Handler")
                        .description("Manage the Request from BBPS Central Unit")
                        .version("1.0"));


    }



}
