package org.arqui.microservicepayment.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para la respuesta de un viaje")
public class TravelResponseDTO {
  @Schema(description = "Id del viaje", example = "1")
  private Long id_travel;
  @Schema(description = "Costo del viaje", example = "150.7")
  private Double costo;
}
