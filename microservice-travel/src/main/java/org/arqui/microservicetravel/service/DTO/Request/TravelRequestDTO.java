package org.arqui.microservicetravel.service.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear un nuevo viaje")
public class TravelRequestDTO {
    @Schema(description = "Fecha y hora de inicio del viaje", example = "2021-01-01T10:00:00")
    private LocalDateTime fecha_hora_inicio;
    @Schema(description = "Fecha y hora de fin del viaje", example = "2021-01-01T11:00:00")
    private LocalDateTime fecha_hora_fin;
    @Schema(description = "Kilometros recorridos durante el viaje", example = "1000")
    private Integer kmRecorridos;
    @Schema(description = "Estado del viaje", example = "true")
    private Boolean pausado;
    @Schema(description = "ID de la parada de inicio del viaje", example = "1")
    private Long parada_inicio;
    @Schema(description = "ID de la parada de fin del viaje", example = "2")
    private Long parada_fin;
    @Schema(description = "ID del monopatin utilizado", example = "SCOOTER-001")
    private String monopatin;
    @Schema(description = "ID del usuario", example = "1")
    private Long usuario;
    @Schema(description = "Costo del viaje", example = "100.0")
    private Double costo;
}
