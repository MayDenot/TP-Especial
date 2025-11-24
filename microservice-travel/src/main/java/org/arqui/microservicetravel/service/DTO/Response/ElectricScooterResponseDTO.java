package org.arqui.microservicetravel.service.DTO.Response;

public class ElectricScooterResponseDTO {
    private Long id;
    private String estado; // DISPONIBLE, EN_USO, EN_MANTENIMIENTO, etc.
    private Long paradaId;
    private Double latitud;
    private Double longitud;
    private Double kilometraje;
}
