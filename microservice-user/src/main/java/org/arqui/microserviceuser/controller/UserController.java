package org.arqui.microserviceuser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para la gestión de usuarios del sistema de monopatines eléctricos")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content
            )
    })

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO nuevoUsuario = userService.save(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //Ver para manejar excepciones de forma global
        }
    }

    @Operation(
            summary = "Actualizar un usuario existente",
            description = "Actualiza los datos de un usuario identificado por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO usuarioActualizado = userService.update(id, userRequestDTO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina un usuario del sistema identificado por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuario eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Retorna los datos de un usuario específico identificado por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(
            @Parameter(description = "ID del usuario a buscar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            UserResponseDTO usuario = userService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Listar todos los usuarios",
            description = "Retorna una lista con todos los usuarios registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        try {
            List<UserResponseDTO> usuarios = userService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Obtener usuarios por rol",
            description = "Filtra y retorna usuarios según su rol en el sistema (ADMIN, USER, etc.)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Rol inválido",
                    content = @Content
            )
    })

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRol(
            @Parameter(description = "Rol del usuario", required = true, example = "USUARIO", schema = @Schema(implementation = Rol.class))
            @PathVariable Rol rol) {
        try {
            List<UserResponseDTO> usuarios = userService.findByRol(rol);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Obtener usuarios que más utilizan monopatines (Administrador)",
            description = "Retorna usuarios con más viajes en un período determinado, filtrados por tipo de cuenta"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetros de fecha o tipo de cuenta inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })

    //e-Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
    //período y por tipo de usuario.
    @GetMapping("/fechaInicio/{inicio}/fechaFin/{fin}/tipoCuenta/{tipoCuenta}")
    public ResponseEntity<?> obtenerUsuariosMasViajesPorPeriodoYTipoCuenta(
            @Parameter(description = "Fecha de inicio del período", required = true, example = "2025-01-01")
            @PathVariable LocalDate inicio,
            @Parameter(description = "Fecha de fin del período", required = true, example = "2025-01-02")
            @PathVariable LocalDate fin,
            @Parameter(description = "Tipo de cuenta (premium, básica)", required = true, example = "premium")
            @PathVariable String tipoCuenta) {
        try {
            List<UserResponseDTO> usuarios =
                    userService.obtenerUsuariosMasViajesPorPeriodoYTipoCuenta(inicio, fin, tipoCuenta);
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Encontrar monopatines cercanos",
            description = "Retorna una lista de monopatines disponibles cercanos a una ubicación geográfica específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de monopatines cercanos (puede estar vacía si no hay disponibles)",
                    content = @Content(schema = @Schema(implementation = ElectricScooterResponseDTO.class))
            )
    })

    //g-Como usuario quiero encontrar los monopatines cercanos a mi zona
    @GetMapping("/cercanos/{latitud}/{longitud}")
    public ResponseEntity<List<ElectricScooterResponseDTO>> obtenerMonopatinesCercanos(
            @Parameter(description = "Latitud de la ubicación", required = true, example = "-37.3217")
            @PathVariable Double latitud,
            @Parameter(description = "Longitud de la ubicación", required = true, example = "-59.1355")
            @PathVariable Double longitud) {
        try {
            List<ElectricScooterResponseDTO> monopatines = userService.obtenerMonopatinesCercanos(latitud, longitud);
            // Siempre retornar 200 OK con la lista (vacía o no)
            return ResponseEntity.ok(monopatines);
        } catch (Exception e) {
            // En caso de error, retornar lista vacía en lugar de error 400
            return ResponseEntity.ok(Collections.emptyList());
        }
    }


    @Operation(
            summary = "Obtener uso de monopatines del usuario",
            description = "Retorna el historial de uso de monopatines de un usuario en un período específico, opcionalmente incluyendo usuarios relacionados a su cuenta"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Información de uso obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    //h-Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si
    //otros usuarios relacionados a mi cuenta los han usado.
    @GetMapping("/{id}/usos")
    public ResponseEntity<Map<String, Object>> obtenerUsoMonopatines(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Fecha de inicio del período", required = true, example = "2025-01-01")
            @RequestParam LocalDate inicio,
            @Parameter(description = "Fecha de fin del período", required = true, example = "2025-01-02")
            @RequestParam LocalDate fin,
            @Parameter(description = "Incluir usuarios relacionados a la cuenta", required = false, example = "false")
            @RequestParam(defaultValue = "false") boolean incluirRelacionados) {
        try {
            Map<String, Object> resultado = userService.obtenerUsoDeMonopatines(id, inicio, fin, incluirRelacionados);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
