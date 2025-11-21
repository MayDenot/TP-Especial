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
import org.arqui.microserviceuser.service.AccountService;
import org.arqui.microserviceuser.service.DTO.request.AccountRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.AccountResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Cuentas", description = "API para la gestión de cuentas de usuarios")
public class AccountController {
    private final AccountService accountService;

    @Operation(
            summary = "Crear una nueva cuenta",
            description = "Crea una nueva cuenta en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cuenta creada exitosamente",
                    content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })

    @PostMapping
    public ResponseEntity<AccountResponseDTO> save(
            @Parameter(description = "Datos de la cuenta a crear", required = true)
            @RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            AccountResponseDTO nuevaCuenta = accountService.save(accountRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCuenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //Ver para manejar excepciones de forma global
        }
    }

    @Operation(
            summary = "Actualizar una cuenta existente",
            description = "Actualiza los datos de una cuenta identificada por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuenta actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cuenta no encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(
            @Parameter(description = "ID de la cuenta a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la cuenta", required = true)
            @RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            AccountResponseDTO cuentaActualizada = accountService.update(id, accountRequestDTO);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Eliminar una cuenta",
            description = "Elimina una cuenta del sistema identificada por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cuenta eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cuenta no encontrada",
                    content = @Content
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la cuenta a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            accountService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Obtener cuenta por ID",
            description = "Retorna los datos de una cuenta específica identificada por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuenta encontrada",
                    content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cuenta no encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(
            @Parameter(description = "ID de la cuenta a buscar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            AccountResponseDTO cuenta = accountService.findById(id);
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Listar todas las cuentas",
            description = "Retorna una lista con todas las cuentas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cuentas obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> findAll() {
        try {
            List<AccountResponseDTO> cuentas = accountService.findAll();
            return ResponseEntity.ok(cuentas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Anular cuenta (Administrador)",
            description = "Permite al administrador anular una cuenta para inhabilitar el uso momentáneo de la aplicación"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuenta anulada exitosamente",
                    content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cuenta no encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error al anular la cuenta",
                    content = @Content
            )
    })

    //b-Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
    // momentáneo de la aplicación.
    @PutMapping("/{id}/anular")
    public ResponseEntity<AccountResponseDTO> anular(
            @Parameter(description = "ID de la cuenta a anular", required = true, example = "1")
            @PathVariable Long id) {
        try {
            AccountResponseDTO cuentaAnulada= accountService.anularCuenta(id);
            return ResponseEntity.ok(cuentaAnulada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Obtener cuenta activa por ID de usuario",
            description = "Busca y retorna la cuenta activa asociada a un usuario específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuenta encontrada",
                    content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró cuenta activa para el usuario",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    // Obtener cuenta activa por usuario ID
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> findByUserId(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long userId) {
        try {
            System.out.println("AccountController: Buscando cuenta para usuario ID: " + userId);
            AccountResponseDTO cuenta = accountService.findByUserId(userId);
            System.out.println("AccountController: Cuenta encontrada - ID: " + cuenta.getId_account() + ", Tipo: " + cuenta.getTipoCuenta());
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException e) {
            System.err.println("AccountController: No se encontró cuenta para usuario " + userId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró cuenta activa para usuario con id: " + userId);
        } catch (Exception e) {
            System.err.println("AccountController: Error interno: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }
}
