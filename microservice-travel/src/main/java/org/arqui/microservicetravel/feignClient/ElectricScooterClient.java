package org.arqui.microservicetravel.feignClient;

import org.arqui.microservicetravel.service.DTO.Response.ElectricScooterResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservice-electricScooter", url = "http://localhost:8082")
public interface ElectricScooterClient {

    @GetMapping("/api/scooters/{id}")
    public ElectricScooterResponseDTO getElectricScooter(@PathVariable String id);

    @PutMapping("/api/scooters/{id}/estado")
    ElectricScooterResponseDTO actualizarEstadoEnParada(
            @PathVariable String id,
            @RequestBody org.arqui.microservicetravel.service.DTO.Request.ActualizarEstadoParadaRequestDTO dto
    );

}
