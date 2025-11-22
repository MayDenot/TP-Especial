package org.arqui.microserviceelectric_scooter.service.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de reporte de uso de Monopatín")
public class ReporteUsoScooterDTO {
    @Schema(description = "Id único del monopatín", example = "SCOOTER-001")
    private String idScooter;
    @Schema(description = "Kilómetros totales recorridos", example = "180.5")
    private Double kilometrosTotales;
    @Schema(description = "Tiempo total de uso en segundos", example = "4200")
    private Long tiempoTotalUsoSegundos;
    @Schema(description = "Tiempo total del monopatín en movimiento en segundos", example = "3200")
    private Long tiempoEnMovimientoSegundos;
    @Schema(description = "Tiempo total del monopatín en pausa en segundos", example = "600")
    private Long tiempoEnPausaSegundos;
    @Schema(description = "Nivel de batería del monopatín (1-100)", example = "85")
    private Double bateriaPorcentaje;
    @Schema(description = "Estado actual del monopatín", example = "disponible")
    private String estado;
}
