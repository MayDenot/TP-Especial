package org.arqui.microservicetravel.service.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para actualizar el estado de una parada")
public class ActualizarEstadoParadaRequestDTO {
    @Schema(description = "Estado de la parada", example = "DISPONIBLE")
    private String estado;  // "DISPONIBLE", "OCUPADO", "REPARADO"
    @Schema(description = "ID de la parada", example = "1")
    private Long idParadaActual;
    @Schema(description = "Latitud de la parada", example = "-34.6037389")
    private Double latitud;
    @Schema(description = "Longitud de la parada", example = "-58.3815701")
    private Double longitud;
}
