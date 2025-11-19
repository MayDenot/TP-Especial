package org.arqui.microservicetravel.service.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para finalizar un viaje")
public class FinalizarViajeRequestDTO {
    @Schema(description = "ID de la parada de inicio del viaje", example = "1")
    private Long paradaInicioId;
    @Schema(description = "ID de la parada de fin del viaje", example = "2")
    private Long paradaFinId;
    @Schema(description = "Latitud de la parada", example = "-34.6037389")
    private Double latitud;
    @Schema(description = "Longitud de la parada", example = "-58.3815701")
    private Double longitud;
}
