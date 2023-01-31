package ru.gb.stalser.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("StAlSer")
                                .description("Kanban доска для координации командной разработки")
                                .version("1.0.0")
                )
                .addServersItem(new Server().url("http://localhost:8081").description("dev"))
                ;
    }
}