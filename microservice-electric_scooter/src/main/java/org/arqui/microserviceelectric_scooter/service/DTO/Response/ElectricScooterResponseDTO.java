package org.arqui.microserviceelectric_scooter.service.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta con información de un Monopatín")
public class ElectricScooterResponseDTO {
    @Schema(description = "Id único del monopatín", example = "SCOOTER-001")
    private String id;
    @Schema(description = "Longitud de la ubicación del monopatín", example = "-59.1355")
    private Double longitud;
    @Schema(description = "Latitud de la ubicación del monopatín", example = "-37.3217")
    private Double latitud;
    @Schema(description = "Indica si el monopatín está habilitado", example = "true")
    private Boolean habilitado;
    @Schema(description = "Nivel de batería del monopatín (1-100)", example = "85")
    private Integer bateria;
    @Schema(description = "Tiempo total de uso en segundos", example = "1200")
    private Long tiempoDeUso;
    @Schema(description = "Kilómetros totales recorridos", example = "125.5")
    private Double kilometrosRecorridos;
    @Schema(description = "Indica si el monopatín está en una parada", example = "true")
    private Boolean enParada;
    @Schema(description = "Código QR único del monopatín", example = "QR123456789")
    private String codigoQR;
    @Schema(description = "Estado actual del monopatín", example = "disponible")
    private String estado;
    @Schema(description = "ID de la parada actual donde se encuentra", example = "5")
    private Long idParadaActual;
    @Schema(description = "Fecha de alta del monopatín", example = "2025-01-01T08:00:00")
    private LocalDateTime fechaAlta;
    @Schema(description = "Fecha de la última actualización", example = "2025-11-19T15:30:00")
    private LocalDateTime ultimaActualizacion;
}
