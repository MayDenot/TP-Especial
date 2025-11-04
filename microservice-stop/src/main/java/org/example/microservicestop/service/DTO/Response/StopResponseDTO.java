package org.example.microservicestop.service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopResponseDTO {
    private long id_stop;
    private String direccion;
    private String nombre;
    private Float latitud;
    private Float longitud;
}
