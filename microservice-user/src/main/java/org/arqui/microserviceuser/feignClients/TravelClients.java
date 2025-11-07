package org.arqui.microserviceuser.feignClients;

import org.arqui.microserviceuser.service.DTO.response.TravelResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "microservice-travel", url = "http://localhost:8084")
public interface TravelClients {
    //3.e
    @GetMapping("/api/travel/fechaInicio/{inicio}/fechaFin/{fin}")
    public List<Long> obtenerIdUsuariosConMasViajes(
            @PathVariable LocalDate fechaInicio,
            @PathVariable LocalDate fechaFin);
    //3.h
    @GetMapping("/api/travels/usuario/{userId}")
    List<TravelResponseDTO> obtenerViajesPorUsuario(
            @PathVariable("userId") Long userId,
            @PathVariable LocalDate fechaInicio,
            @PathVariable LocalDate fechaFin);

}
