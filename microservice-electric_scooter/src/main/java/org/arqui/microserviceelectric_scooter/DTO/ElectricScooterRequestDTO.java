package org.arqui.microserviceelectric_scooter.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooterRequestDTO {
    private Double longitud;
    private Double latitud;
    private Boolean habilitado;
    private Integer bateria;
    private Long tiempoDeUso;
    private Double kilometrosRecorridos;
    private Boolean enParada;
    private String codigoQR; // opcional, puede generarse en el backend
    private String estado;   // lo recibís como string (por ejemplo "DISPONIBLE")
    private Long idParadaActual;
}

