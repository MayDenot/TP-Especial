package org.arqui.microserviceuser.controller;

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
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> save(@RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            AccountResponseDTO nuevaCuenta = accountService.save(accountRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCuenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //Ver para manejar excepciones de forma global
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(@PathVariable Long id, @RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            AccountResponseDTO cuentaActualizada = accountService.update(id, accountRequestDTO);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            accountService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        try {
            AccountResponseDTO cuenta = accountService.findById(id);
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> findAll() {
        try {
            List<AccountResponseDTO> cuentas = accountService.findAll();
            return ResponseEntity.ok(cuentas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //b-Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
    // momentáneo de la aplicación.
    @PutMapping("/{id}/anular")
    public ResponseEntity<AccountResponseDTO> anular(@PathVariable Long id) {
        try {
            AccountResponseDTO cuentaAnulada= accountService.anularCuenta(id);
            return ResponseEntity.ok(cuentaAnulada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Obtener cuenta activa por usuario ID
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
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
