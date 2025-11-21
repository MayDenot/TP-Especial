package org.arqui.microserviceuser.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta con información de las pausas del monopatín")
public class Pause {
    @Schema(description = "ID de la pausa", example = "1")
    private Integer id_pause;
    @Schema(description = "Fecha y hora del inicio de la pausa", example = "2025-04-19T10:45")
    private LocalDateTime hora_inicio;
    @Schema(description = "Fecha y hora del fin de la pausa", example = "2025-04-19T10:55")
    private LocalDateTime hora_fin;
}
