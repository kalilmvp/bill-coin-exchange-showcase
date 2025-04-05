package com.kmvp.billcoinexhange.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kalil.peixoto
 * @date 4/4/25 19:32
 * @email kalilmvp@gmail.com
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bill Coin Exchange API")
                        .version("1.0")
                        .description("API documentation for the Bill Coin Exchange API"));
    }
}
