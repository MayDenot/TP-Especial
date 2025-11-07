package org.arqui.microserviceelectric_scooter.service.DTO.Request;



import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooterRequestDTO {
    @DecimalMin(value = "-90.0", message = "La latitud debe estar entre -90 y 90")
    @DecimalMax(value = "90.0", message = "La latitud debe estar entre -90 y 90")
    private Double latitud;

    @DecimalMin(value = "-180.0", message = "La longitud debe estar entre -180 y 180")
    @DecimalMax(value = "180.0", message = "La longitud debe estar entre -180 y 180")
    private Double longitud;
    private Boolean habilitado;
    private Integer bateria;
    private Long tiempoDeUso;
    private Double kilometrosRecorridos;
    private Boolean enParada;
    private String codigoQR; // opcional, puede generarse en el backend
    private String estado;   // lo recibís como string (por ejemplo "DISPONIBLE")
    private Long idParadaActual;
}

