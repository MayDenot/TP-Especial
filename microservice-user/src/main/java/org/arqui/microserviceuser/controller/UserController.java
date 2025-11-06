package org.arqui.microserviceuser.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arqui.microserviceuser.service.DTO.response.ElectricScooterResponseDTO;
import org.arqui.microserviceuser.Rol;
import org.arqui.microserviceuser.service.DTO.request.UserRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.UserResponseDTO;
import org.arqui.microserviceuser.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO nuevoUsuario = userService.save(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //Ver para manejar excepciones de forma global
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO usuarioActualizado = userService.update(id, userRequestDTO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        try {
            UserResponseDTO usuario = userService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        try {
            List<UserResponseDTO> usuarios = userService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRol(@PathVariable Rol rol) {
        try {
            List<UserResponseDTO> usuarios = userService.findByRol(rol);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //e-Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
    //período y por tipo de usuario.
    @GetMapping("/fechaInicio/{inicio}/fechaFin/{fin}")
    public ResponseEntity<List<UserResponseDTO>> obtenerUsuariosMasViajesPorPeriodoYTipoCuenta(
            @PathVariable LocalDate inicio,
            @PathVariable LocalDate fin,
            @PathVariable String tipoCuenta) {
        try {
            List<UserResponseDTO> usuarios =
                    userService.obtenerUsuariosMasViajesPorPeriodoYTipoCuenta(inicio, fin, tipoCuenta);
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //g-Como usuario quiero encontrar los monopatines cercanos a mi zona
    @GetMapping("/cercanos/{latitud}/{longitud}")
    public ResponseEntity<List<ElectricScooterResponseDTO>> obtenerMonopatinesCercanos(@PathVariable Double latitud, @PathVariable Double longitud) {
        try {
            List<ElectricScooterResponseDTO> monopatines = userService.obtenerMonopatinesCercanos(latitud, longitud);
            return ResponseEntity.ok(monopatines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
