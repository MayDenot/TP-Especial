package org.arqui.microservicegateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway - Sistema de Monopatines")
                        .version("1.0.0")
                        .description("""
                                Gateway principal que centraliza el acceso a todos los microservicios del sistema.
                                
                                **Microservicios disponibles:**
                                - Monopatines (Scooters)
                                - Paradas (Stops)
                                - Viajes (Travels)
                                - Usuarios (Users & Accounts)
                                - Pagos (Rates)
                                - Autenticación (JWT)
                                """)
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@scooters.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8089")
                                .description("Gateway - Puerto 8089")
                ));
    }

    @Bean
    public GroupedOpenApi authenticationApi() {
        return GroupedOpenApi.builder()
                .group("1-autenticacion")
                .displayName("Autenticación")
                .pathsToMatch("/api/authenticate/**", "/api/usuarios/**")
                .build();
    }

    @Bean
    public GroupedOpenApi scootersApi() {
        return GroupedOpenApi.builder()
                .group("2-monopatines")
                .displayName("Monopatines")
                .pathsToMatch("/api/scooters/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stopsApi() {
        return GroupedOpenApi.builder()
                .group("3-paradas")
                .displayName("Paradas")
                .pathsToMatch("/api/stops/**")
                .build();
    }

    @Bean
    public GroupedOpenApi travelsApi() {
        return GroupedOpenApi.builder()
                .group("4-viajes")
                .displayName("Viajes")
                .pathsToMatch("/api/travels/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("5-usuarios")
                .displayName("Usuarios y Cuentas")
                .pathsToMatch("/api/users/**", "/api/accounts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentsApi() {
        return GroupedOpenApi.builder()
                .group("6-pagos")
                .displayName("Tarifas y Pagos")
                .pathsToMatch("/api/rates/**")
                .build();
    }
}
