package org.arqui.microserviceuser.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooterResponseDTO {
    private String id;
    private Double longitud;
    private Double latitud;
    private Boolean habilitado;
    private Integer bateria;
    private Long tiempoDeUso;
    private Double kilometrosRecorridos;
    private Boolean enParada;
    private String codigoQR;
    private String estado;
    private Long idParadaActual;
    private LocalDateTime fechaAlta;
    private LocalDateTime ultimaActualizacion;
}
