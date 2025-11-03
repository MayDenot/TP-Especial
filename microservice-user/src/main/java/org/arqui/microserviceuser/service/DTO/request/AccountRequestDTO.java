package org.arqui.microserviceuser.service.DTO.request;

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
public class AccountRequestDTO {
    @NotNull(message = "La fecha de alta es obligatoria")
    @PastOrPresent(message = "La fecha de alta no puede ser posterior a la fecha actual")
    private LocalDateTime fechaDeAlta;
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto no puede ser negativo")
    private Double montoDisponible;

    private boolean activa;

    private String tipoCuenta;

}
