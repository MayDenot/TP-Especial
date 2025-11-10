package org.arqui.microservicepayment.service.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateRequestDTO {
  @NotNull(message = "El precio normal no puede ser nulo")
  private Double tarifa;
  @NotNull(message = "El precio extra no puede ser nulo")
  private Double tarifaExtra;
  @NotNull(message = "La fecha no puede ser nula")
  private LocalDateTime fechaActualizacion;
  @NotNull(message = "La vigencia no puede ser nula")
  private Boolean vigente;
}
