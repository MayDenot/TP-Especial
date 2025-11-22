package org.arqui.microserviceelectric_scooter.service.DTO.Request;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microserviceelectric_scooter.EstadoScooter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para agregar un monopatín")
public class ElectricScooterRequestDTO {
    @Schema(description = "Latitud de la ubicación del monopatín", example = "-37.3217")
    @DecimalMin(value = "-55.0", message = "La latitud debe estar en rango válido para Argentina")
    @DecimalMax(value = "-21.0", message = "La latitud debe estar en rango válido para Argentina")
    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    // Para Argentina, las longitudes están entre -73 y -53
    @Schema(description = "Longitud de la ubicación del usuario", example = "-59.1355")
    @DecimalMin(value = "-73.0", message = "La longitud debe estar en rango válido para Argentina")
    @DecimalMax(value = "-53.0", message = "La longitud debe estar en rango válido para Argentina")
    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    @Schema(description = "Indica si el monopatín está habilitado", example = "true")
    @NotNull(message = "El campo 'habilitado' es obligatorio")
    private Boolean habilitado;

    @Schema(description = "Nivel de batería del monopatín (1-100)", example = "85")
    @NotNull(message = "El campo 'batería' es obligatorio")
    @Min(value = 1, message = "La batería debe ser mayor a 0")
    @Max(value = 100, message = "La batería no puede ser mayor que 100")
    private Integer bateria;

    @Schema(description = "Tiempo total de uso en segundos", example = "1200")
    @NotNull(message = "El tiempo de uso es obligatorio")
    @PositiveOrZero(message = "El tiempo de uso no puede ser negativo")
    private Long tiempoDeUso;

    @Schema(description = "Kilómetros totales recorridos", example = "125.5")
    @NotNull(message = "Los kilómetros recorridos son obligatorios")
    @PositiveOrZero(message = "Los kilómetros recorridos no pueden ser negativos")
    private Double kilometrosRecorridos;

    @Schema(description = "Indica si el monopatín está en una parada", example = "true")
    @NotNull(message = "El campo 'enParada' es obligatorio")
    private Boolean enParada;

    @Schema(description = "Código QR único del monopatín", example = "QR123456789")
    private String codigoQR; // opcional

    @Schema(description = "Estado actual del monopatín", example = "disponible")
    @NotBlank(message = "El estado es obligatorio")
    private EstadoScooter estado;

    @Schema(description = "ID de la parada actual donde se encuentra", example = "5")
    private Long idParadaActual;
}

