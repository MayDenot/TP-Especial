package org.arqui.microservicepayment.service.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelWithCostRequestDTO {
    private Long id_travel;
    private LocalDateTime fecha_hora_inicio;
    private Integer kmRecorridos;
    private Long usuario;
    private String tipoCuenta;
    private Double costoBase;
    private Double costoExtra;
    private Double costoTotal;
    private Boolean tienePausaLarga;
}
