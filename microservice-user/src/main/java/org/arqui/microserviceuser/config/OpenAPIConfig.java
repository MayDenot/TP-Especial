package org.arqui.microserviceuser.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Microservicio User")
                        .version("1.0.0")
                        .description("Documentación de la API del microservicio de usuarios y cuentas")
                        .contact(new Contact()
                                .name("Elisa Guaspari")
                                .email("eliguaspari@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8085")
                                .description("Microservicio User (desarrollo)"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Gateway API")
                ));
    }

}
