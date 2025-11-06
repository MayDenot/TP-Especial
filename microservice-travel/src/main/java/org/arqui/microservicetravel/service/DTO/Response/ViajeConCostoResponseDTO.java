package org.arqui.microservicetravel.service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajeConCostoResponseDTO {
    private Long id_travel;
    private LocalDateTime fecha_hora_inicio;
    private Integer kmRecorridos;
    private Long usuario;
    private String tipoCuenta; // PREMIUM o BASIC
    private Double costoBase;
    private Double costoExtra;
    private Double costoTotal;
    private Boolean tienePausaLarga; // > 15 minutos
}
