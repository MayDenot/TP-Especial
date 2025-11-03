package org.arqui.microserviceuser.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microserviceuser.Rol;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id_user;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Integer numeroCelular;
    private Rol rol;
    private Float latitud;
    private Float longitud;
}
