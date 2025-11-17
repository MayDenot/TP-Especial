package org.arqui.microservicetravel.service.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequestDTO {
    private LocalDateTime fecha_hora_inicio;
    private LocalDateTime fecha_hora_fin;
    private Integer kmRecorridos;
    private Boolean pausado;
    private Long parada_inicio;
    private Long parada_fin;
    private String monopatin;
    private Long usuario;
    private Double costo;
}
