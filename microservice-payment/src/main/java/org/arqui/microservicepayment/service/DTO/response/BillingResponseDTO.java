package org.arqui.microservicepayment.service.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la respuesta de una factura")
public class BillingResponseDTO {
  @Schema(description = "Año de la factura", example = "2024")
  private Integer anio;
  @Schema(description = "Mes inicio de la factura", example = "09")
  private Integer mesInicio;
  @Schema(description = "Mes fin de la factura", example = "12")
  private Integer mesFin;
  @Schema(description = "Total de la factura", example = "150.7")
  private Double total;
}
