package org.arqui.microserviceuser.feignClients;

import org.arqui.microserviceuser.service.DTO.response.TravelResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "microservice-travel", url = "http://localhost:8084")
public interface TravelClients {
    //3.e
    @GetMapping("/api/travels/fechaInicio/{inicio}/fechaFin/{fin}")
    public List<Long> obtenerIdUsuariosConMasViajes(
            @PathVariable("inicio") String fechaInicio,
            @PathVariable("fin") String fechaFin);
    //3.h
    @GetMapping("/api/travels/usuario/{userId}")
    List<TravelResponseDTO> obtenerViajesPorUsuario(
            @PathVariable("userId") Long userId,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin);
}
