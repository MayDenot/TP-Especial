package org.arqui.microserviceuser.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Integer numeroCelular;
    //Rol va acá??
    //Ver lo de latitud y longitud
}
