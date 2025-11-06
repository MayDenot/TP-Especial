package org.arqui.microservicepayment.service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingResponseDTO {
  private Integer anio;
  private Integer mesInicio;
  private Integer mesFin;
  private Double totalFacturado;
  private Integer totalViajes;
  private Double totalTarifasBase;
  private Double totalTarifasExtra;
  private Double totalDescuentosPremium;
}
