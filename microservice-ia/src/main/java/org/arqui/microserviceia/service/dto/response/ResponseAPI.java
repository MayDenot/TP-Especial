package org.arqui.microserviceia.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseAPI<T> {
  private boolean ok;
  private String mensaje;
  private T datos;
}
