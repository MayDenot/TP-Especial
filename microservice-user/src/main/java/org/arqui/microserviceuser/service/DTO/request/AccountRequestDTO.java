package org.arqui.microserviceuser.service.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar una cuenta")
public class AccountRequestDTO {
    @Schema(description = "Fecha de alta de la cuenta", example = "2025-01-15T10:30:00", required = true)
    @NotNull(message = "La fecha de alta es obligatoria")
    @PastOrPresent(message = "La fecha de alta no puede ser posterior a la fecha actual")
    private LocalDateTime fechaDeAlta;
    @Schema(description = "Monto disponible de la cuenta", example = "1500.00", required = true)
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto no puede ser negativo")
    private Double montoDisponible;

    @Schema(description = "Indica si la cuenta está activa", example = "true")
    private boolean activa;
    @Schema(description = "Tipo de cuenta del usuario",  example = "básica")
    private String tipoCuenta;

}
