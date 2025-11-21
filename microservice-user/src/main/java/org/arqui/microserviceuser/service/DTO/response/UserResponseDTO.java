package org.arqui.microserviceuser.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microserviceuser.Rol;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta con información de un usuario")
public class UserResponseDTO {
    @Schema(description = "ID único del usuario", example = "1")
    private Long id_user;

    @Schema(description = "Nombre de usuario", example = "jperez")
    private String nombreUsuario;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Schema(description = "Email del usuario", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Número de celular del usuario", example = "2494123456")
    private Integer numeroCelular;

    @Schema(description = "Rol del usuario en el sistema", example = "USER")
    private Rol rol;

    @Schema(description = "Latitud de la ubicación del usuario", example = "-37.3217")
    private Float latitud;

    @Schema(description = "Longitud de la ubicación del usuario", example = "-59.1355")
    private Float longitud;
}
