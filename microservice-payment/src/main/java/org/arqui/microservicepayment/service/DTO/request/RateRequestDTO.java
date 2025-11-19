package org.arqui.microservicepayment.service.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para crear una nueva tarifa")
public class RateRequestDTO {
  @Schema(description = "Costo de la tarifa normal", example = "60.0")
  @NotNull(message = "El precio normal no puede ser nulo")
  private Double tarifa;
  @Schema(description = "Costo de la tarifa extra", example = "100.0")
  @NotNull(message = "El precio extra no puede ser nulo")
  private Double tarifaExtra;
  @Schema(description = "Fecha de inicio de la tarifa", example = "2020-12-03T10:15:30")
  @NotNull(message = "La fecha no puede ser nula")
  private LocalDateTime fechaInicio;
}
