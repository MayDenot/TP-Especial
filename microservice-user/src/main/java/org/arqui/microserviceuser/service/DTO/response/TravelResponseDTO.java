package org.arqui.microserviceuser.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la respuesta de viajes")
public class TravelResponseDTO {
    @Schema(description = "ID del viaje", example = "1")
    private Long id_travel;
    @Schema(description = "Fecha y hora del inicio del viaje", example = "2025-04-19T10:45")
    private LocalDateTime fecha_hora_inicio;
    @Schema(description = "Fecha y hora del fin del viaje", example = "2025-04-19T11:45")
    private LocalDateTime fecha_hora_fin;
    @Schema(description = "Kilómetros recorridos durante el viaje", example = "1000")
    private Integer kmRecorridos;
    @Schema(description = "Estado del viaje", example = "true")
    private Boolean pausado;
    @Schema(description = "Lista de pausas del viaje")
    private List<Pause> pausas;
    @Schema(description = "Costo del viaje", example = "100.0")
    private Long tarifa;
    @Schema(description = "ID de la parada de inicio del viaje", example = "1")
    private Long parada_inicio;
    @Schema(description = "ID de la parada de fin del viaje", example = "2")
    private Long parada_fin;
    @Schema(description = "ID del monopatin utilizado", example = "SCOOTER-001")
    private String monopatin;
    @Schema(description = "ID del usuario", example = "1")
    private Long usuario;
}
