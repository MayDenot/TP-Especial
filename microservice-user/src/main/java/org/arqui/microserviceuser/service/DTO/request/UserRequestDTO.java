package org.arqui.microserviceuser.service.DTO.request;

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
public class UserRequestDTO {

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @NotEmpty(message="El nombre de usuario es obligatorio")
    private String nombreUsuario;
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message="El nombre es obligatorio")
    private String nombre;
    @NotNull(message = "El apellido no puede ser nulo")
    @NotEmpty(message="El apellido es obligatorio")
    private String apellido;
    @Email(message = "El email no tiene un formato válido")
    @NotEmpty(message = "El email es obligatorio")
    private String email;
    private Integer numeroCelular;
    private Rol rol;
    private float latitud;
    private float longitud;
}
