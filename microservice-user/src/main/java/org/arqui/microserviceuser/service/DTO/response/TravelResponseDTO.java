package org.arqui.microserviceuser.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Long tarifa;
    private Long parada_inicio;
    private Long parada_fin;
    private String monopatin;
    private Long usuario;
}
