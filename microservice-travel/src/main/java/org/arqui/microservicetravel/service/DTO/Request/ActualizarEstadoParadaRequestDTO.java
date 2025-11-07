package org.arqui.microservicetravel.service.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoParadaRequestDTO {
    private String estado;  // "DISPONIBLE", "OCUPADO", "REPARADO"
    private Long idParadaActual;
    private Double latitud;
    private Double longitud;
}
