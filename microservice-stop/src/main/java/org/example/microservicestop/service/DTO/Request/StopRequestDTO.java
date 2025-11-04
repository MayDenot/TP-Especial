package org.example.microservicestop.service.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopRequestDTO {
    private String direccion;
    private String nombre;
    private Float latitud;
    private Float longitud;
}
