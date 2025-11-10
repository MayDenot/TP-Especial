package org.arqui.microserviceelectric_scooter.service.DTO.Request;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooterRequestDTO {

    @DecimalMin(value = "-90.0", message = "La latitud debe estar entre -90 y 90")
    @DecimalMax(value = "90.0", message = "La latitud debe estar entre -90 y 90")
    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    @DecimalMin(value = "-180.0", message = "La longitud debe estar entre -180 y 180")
    @DecimalMax(value = "180.0", message = "La longitud debe estar entre -180 y 180")
    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    @NotNull(message = "El campo 'habilitado' es obligatorio")
    private Boolean habilitado;

    @NotNull(message = "El campo 'batería' es obligatorio")
    @Min(value = 0, message = "La batería no puede ser menor que 0")
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

