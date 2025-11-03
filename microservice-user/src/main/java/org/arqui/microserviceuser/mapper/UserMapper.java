package org.arqui.microserviceuser.mapper;

import org.arqui.microserviceuser.entity.User;
import org.arqui.microserviceuser.service.DTO.request.UserRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.UserResponseDTO;

public class UserMapper {

    public static User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setNombreUsuario(userRequestDTO.getNombreUsuario());
        user.setNombre(userRequestDTO.getNombre());
        user.setApellido(userRequestDTO.getApellido());
        user.setEmail(userRequestDTO.getEmail());
        user.setNumeroCelular(userRequestDTO.getNumeroCelular());
        user.setRol(userRequestDTO.getRol());
        user.setLatitud(userRequestDTO.getLatitud());
        user.setLongitud(userRequestDTO.getLongitud());

        return user;
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId_user(),
                user.getNombreUsuario(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail(),
                user.getNumeroCelular(),
                user.getRol(),
                user.getLatitud(),
                user.getLongitud()
        );
    }


}
