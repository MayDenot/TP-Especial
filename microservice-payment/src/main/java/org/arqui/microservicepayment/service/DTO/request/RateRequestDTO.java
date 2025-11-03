package org.arqui.microservicepayment.service.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateRequestDTO {
  private Double tarifa;
  private Double tarifaExtra;
  private LocalDateTime fecha;
}
