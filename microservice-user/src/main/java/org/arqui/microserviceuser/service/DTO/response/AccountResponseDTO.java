package org.arqui.microserviceuser.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "DTO de respuesta con información de una cuenta")
public class AccountResponseDTO {
    @Schema(description = "ID único de la cuenta", example = "1")
    private Long id_account;
    @Schema(description = "Fecha de alta de la cuenta", example = "2025-01-15T10:30:00")
    private LocalDateTime fechaDeAlta;
    @Schema(description = "Monto disponible en la cuenta", example = "10000.00")
    private Double montoDisponible;
    @Schema(description = "Indica si la cuenta está activa", example = "true")
    private boolean activa;
    @Schema(description = "Tipo de cuenta del usuario", example = "básica")
    private String tipoCuenta;
}
