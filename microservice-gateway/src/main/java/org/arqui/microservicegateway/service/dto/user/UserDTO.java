package org.arqui.microservicegateway.service.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Datos para registrar un nuevo usuario")
public class UserDTO {

    @Schema(description = "Nombre de usuario único", example = "juan.perez", required = true)
    @NotNull( message = "El usuario es un campo requerido." )
    @NotEmpty( message = "El usuario es un campo requerido." )
    private String username;

    @Schema(description = "Contraseña del usuario", example = "SecurePass123!", required = true)
    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;

    @Schema(
            description = "Roles asignados al usuario. Valores posibles: USUARIO, ADMINISTRADOR, MANTENIMIENTO",
            example = "[\"USUARIO\", \"ADMINISTRADOR\"]",
            allowableValues = {"USUARIO", "ADMINISTRADOR", "MANTENIMIENTO"},
            required = true
    )
    @NotNull( message = "Los roles son un campo requerido." )
    @NotEmpty( message = "Los roles son un campo requerido." )
    private Set<String> authorities;
}
