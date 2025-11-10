package org.arqui.microserviceuser.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pause {
    private Integer id_pause;
    private LocalDateTime hora_inicio;
    private LocalDateTime hora_fin;
}
