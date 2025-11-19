package org.arqui.microservicepayment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("API de Microservicio Payment")
                    .version("1.0.0")
                    .description("Documentación de la API del microservicio payment")
                    .contact(new Contact()
                            .name("Mayra Andrea Denot")
                            .email("mayra.denot@gmail.com"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://springdoc.org")));
  }
}
