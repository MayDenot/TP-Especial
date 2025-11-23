package org.arqui.microservicegateway.service.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos de autenticación del usuario")
public class LoginDTO {

    @Schema(description = "Nombre de usuario", example = "jfana", required = true)
    @NotNull( message = "El usuario es un campo requerido." )
    @NotEmpty( message = "El usuario es un campo requerido." )
    private String username;

    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;

    public String toString(){
        return "Username: " + username + ", Password: [FORBIDDEN] ";
    }
}
