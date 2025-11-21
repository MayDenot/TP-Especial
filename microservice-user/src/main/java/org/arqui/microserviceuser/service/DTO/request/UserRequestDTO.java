package org.arqui.microserviceuser.service.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.arqui.microserviceuser.Rol;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar un usuario")
public class UserRequestDTO {
    @Schema(description = "Nombre de usuario único", example = "jperez", required = true)
    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @NotEmpty(message="El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message="El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    @NotNull(message = "El apellido no puede ser nulo")
    @NotEmpty(message="El apellido es obligatorio")
    private String apellido;

    @Schema(description = "Email del usuario", example = "juan.perez@example.com", required = true)
    @Email(message = "El email no tiene un formato válido")
    @NotEmpty(message = "El email es obligatorio")
    private String email;

    @Schema(description = "Número de celular del usuario", example ="2494123456")
    private Integer numeroCelular;

    @Schema(description = "Rol del usuario en el sistema", example = "USER", allowableValues = {"USER", "ADMINISTRADOR"})
    private Rol rol;

    @Schema(description = "Latitud de la ubicación del usuario", example = "-37.3217")
    private float latitud;

    @Schema(description = "Longitud de la ubicación del usuario", example = "-59.1355")
    private float longitud;
}
