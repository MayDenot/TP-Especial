package org.arqui.microserviceuser.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceuser.Rol;
import org.arqui.microserviceuser.entity.User;
import org.arqui.microserviceuser.feignClients.ElectricScooterClients;
import org.arqui.microserviceuser.mapper.UserMapper;
import org.arqui.microserviceuser.repository.UserRepository;
import org.arqui.microserviceuser.service.DTO.request.UserRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.UserResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ElectricScooterClients electricScooterClients;

    @Transactional
    public UserResponseDTO save(UserRequestDTO user) {
        User usuario = UserMapper.toEntity(user);
        User nuevoUsuario = userRepository.save(usuario);
        return UserMapper.toResponse(nuevoUsuario);
    }


    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombreUsuario(userRequestDTO.getNombreUsuario());
        usuario.setNombre(userRequestDTO.getNombre());
        usuario.setApellido(userRequestDTO.getApellido());
        usuario.setEmail(userRequestDTO.getEmail());
        usuario.setNumeroCelular(userRequestDTO.getNumeroCelular());
        usuario.setRol(userRequestDTO.getRol());
        usuario.setLatitud(userRequestDTO.getLatitud());
        usuario.setLongitud(userRequestDTO.getLongitud());

        User usuarioActualizado = userRepository.save(usuario);
        return UserMapper.toResponse(usuarioActualizado);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return UserMapper.toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    // Metodo para buscar por rol
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findByRol(Rol rol) {
        return userRepository.findByRol(rol)
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    //e-Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
    //período y por tipo de usuario.
    @Transactional(readOnly = true)
    public List<UserResponseDTO> obtenerUsuariosMasViajesPorPeriodoYTipoCuenta(

    //g-Como usuario quiero encontrar los monopatines cercanos a mi zona
    @Transactional(readOnly = true)
    public List<ElectricScooterResponseDTO> obtenerMonopatinesCercanos(Double latitud, Double longitud) {

        List<ElectricScooterResponseDTO> monopatines = electricScooterClients.obtenerMonopatinesCercanos(latitud, longitud);

        if (monopatines == null || monopatines.isEmpty()) {
            throw new RuntimeException("No se encontraron monopatines cercanos en la zona especificada.");
        }

        return monopatines;
    }

}