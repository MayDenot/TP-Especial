package org.arqui.microservicetravel.service.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalizarViajeRequestDTO {
    private Long paradaFinId;
    private Double latitud;
    private Double longitud;
}
