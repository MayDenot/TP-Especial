package org.arqui.microservicetravel.service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateInfoResponseDTO {
    private Long id;
    private Double tarifa;
    private Double tarifaExtra;
}
