package org.arqui.microserviceuser.service;

import lombok.RequiredArgsConstructor;

import org.arqui.microserviceuser.Rol;
import org.arqui.microserviceuser.entity.Account;
import org.arqui.microserviceuser.entity.User;
import org.arqui.microserviceuser.feignClients.ElectricScooterClients;
import org.arqui.microserviceuser.feignClients.TravelClients;
import org.arqui.microserviceuser.mapper.UserMapper;
import org.arqui.microserviceuser.repository.UserRepository;
import org.arqui.microserviceuser.service.DTO.request.UserRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ElectricScooterClients electricScooterClients;
    private final TravelClients travelClients;

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
    public List<UserResponseDTO> obtenerUsuariosMasViajesPorPeriodoYTipoCuenta(LocalDate fechaInicio, LocalDate fechaFin, String tipoCuenta) {
        List<Long> idsUsuarios = travelClients.obtenerIdUsuariosConMasViajes(fechaInicio, fechaFin);

        if (idsUsuarios == null || idsUsuarios.isEmpty()) {
            throw new RuntimeException("No se encontraron usuarios con viajes en el período indicado.");
        }


        List<User> usuarios = userRepository.findByIdsAndTipoCuenta(idsUsuarios, tipoCuenta);

        if (usuarios.isEmpty()) {
            throw new RuntimeException("No se encontraron usuarios con cuenta de tipo " + tipoCuenta);
        }

        return usuarios.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    //g-Como usuario quiero encontrar los monopatines cercanos a mi zona
    @Transactional(readOnly = true)
    public List<ElectricScooterResponseDTO> obtenerMonopatinesCercanos(Double latitud, Double longitud) {

        List<ElectricScooterResponseDTO> monopatines = electricScooterClients.obtenerMonopatinesCercanos(latitud, longitud);

        if (monopatines == null || monopatines.isEmpty()) {
            throw new RuntimeException("No se encontraron monopatines cercanos en la zona especificada.");
        }

        return monopatines;
    }

    //h-Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si
    //otros usuarios relacionados a mi cuenta los han usado.
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerUsoDeMonopatines(Long userId, LocalDate fechaInicio, LocalDate fechaFin, boolean incluirRelacionados) {

        List<TravelResponseDTO> viajesUsuario = travelClients.obtenerViajesPorUsuario(userId, fechaInicio, fechaFin);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("usuarioPrincipal", userRepository.findById(userId)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + userId)));
        resultado.put("viajesUsuario", viajesUsuario);

        // Usuarios relacionados
        if (incluirRelacionados) {
            List<Long> idsCuentas = userRepository.findById(userId)
                    .map(u -> u.getCuentas().stream().map(Account::getId_account).toList())
                    .orElse(List.of());

            List<User> relacionados = userRepository.findOtrosUsuariosDeMismasCuentas(idsCuentas, userId);

            List<Map<String, Object>> viajesRelacionados = new ArrayList<>();
            for (User rel : relacionados) {
                List<TravelResponseDTO> viajesRel = travelClients.obtenerViajesPorUsuario(rel.getId_user(), fechaInicio, fechaFin);
                viajesRelacionados.add(Map.of(
                        "usuario", UserMapper.toResponse(rel),
                        "viajes", viajesRel
                ));
            }
            resultado.put("usuariosRelacionados", viajesRelacionados);
        }

        return resultado;
    }

}