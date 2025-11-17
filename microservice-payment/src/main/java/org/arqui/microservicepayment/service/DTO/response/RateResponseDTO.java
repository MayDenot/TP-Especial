package org.arqui.microservicepayment.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateResponseDTO {
  private Long id;
  private Double tarifa;
  private Double tarifaExtra;
  private LocalDateTime fechaInicio;
}
