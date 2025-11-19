package org.arqui.microservicepayment.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para la respuesta de una tarifa")
public class RateResponseDTO {
  @Schema(description = "Id de la tarifa", example = "1")
  private Long id;
  @Schema(description = "Costo de la tarifa", example = "60.0")
  private Double tarifa;
  @Schema(description = "Costo de la tarifa extra", example = "100.0")
  private Double tarifaExtra;
  @Schema(description = "Fecha de inicio de la tarifa", example = "2020-12-03T10:15:30")
  private LocalDateTime fechaInicio;
}
