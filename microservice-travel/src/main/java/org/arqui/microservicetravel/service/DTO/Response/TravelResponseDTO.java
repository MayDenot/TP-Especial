package org.arqui.microservicetravel.service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microservicetravel.entity.Pause;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelResponseDTO {
    private Long id_travel;
    private LocalDateTime fecha_hora_inicio;
    private LocalDateTime fecha_hora_fin;
    private Integer kmRecorridos;
    private Boolean pausado;
    private List<Pause> pausas;
    private Long parada_inicio;
    private Long parada_fin;
    private String monopatin;
    private Long usuario;
    private Double costo;
}
