package org.arqui.microserviceelectric_scooter.service.DTO.Request;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooterRequestDTO {

    @DecimalMin(value = "-55.0", message = "La latitud debe estar en rango válido para Argentina")
    @DecimalMax(value = "-21.0", message = "La latitud debe estar en rango válido para Argentina")
    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    // Para Argentina, las longitudes están entre -73 y -53
    @DecimalMin(value = "-73.0", message = "La longitud debe estar en rango válido para Argentina")
    @DecimalMax(value = "-53.0", message = "La longitud debe estar en rango válido para Argentina")
    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    @NotNull(message = "El campo 'habilitado' es obligatorio")
    private Boolean habilitado;

    @NotNull(message = "El campo 'batería' es obligatorio")
    @Min(value = 1, message = "La batería debe ser mayor a 0")
    @Max(value = 100, message = "La batería no puede ser mayor que 100")
    private Integer bateria;

    @NotNull(message = "El tiempo de uso es obligatorio")
    @PositiveOrZero(message = "El tiempo de uso no puede ser negativo")
    private Long tiempoDeUso;

    @NotNull(message = "Los kilómetros recorridos son obligatorios")
    @PositiveOrZero(message = "Los kilómetros recorridos no pueden ser negativos")
    private Double kilometrosRecorridos;

    @NotNull(message = "El campo 'enParada' es obligatorio")
    private Boolean enParada;

    private String codigoQR; // opcional

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private Long idParadaActual;
}

