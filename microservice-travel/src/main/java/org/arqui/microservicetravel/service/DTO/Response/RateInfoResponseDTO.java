package org.arqui.microservicetravel.service.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la respuesta de una tarifa")
public class RateInfoResponseDTO {
    @Schema(description = "ID de la tarifa", example = "1")
    private Long id;
    @Schema(description = "Precio base de la tarifa", example = "100.0")
    private Double tarifa;
    @Schema(description = "Precio extra de la tarifa", example = "150.0")
    private Double tarifaExtra;
}
