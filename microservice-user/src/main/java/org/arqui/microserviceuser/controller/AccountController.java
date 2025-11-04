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
    public ResponseEntity<AccountResponseDTO> save(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            AccountResponseDTO nuevaCuenta = accountService.save(accountRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCuenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //Ver para manejar excepciones de forma global
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AccountRequestDTO accountRequestDTO) {
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
}
