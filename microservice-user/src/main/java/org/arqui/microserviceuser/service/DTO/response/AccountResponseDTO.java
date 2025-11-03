package org.arqui.microserviceuser.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountResponseDTO {
    private LocalDateTime fechaDeAlta;
    private Double montoDisponible;
    private boolean activa;
    private String tipoCuenta;
}
