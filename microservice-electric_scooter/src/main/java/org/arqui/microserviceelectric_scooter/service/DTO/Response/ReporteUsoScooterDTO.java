package org.arqui.microserviceelectric_scooter.service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteUsoScooterDTO {
    private String idScooter;
    private Double kilometrosTotales;
    private Long tiempoTotalUsoSegundos;
    private Long tiempoEnMovimientoSegundos;
    private Long tiempoEnPausaSegundos;
    private Double bateriaPorcentaje;
    private String estado;
}
