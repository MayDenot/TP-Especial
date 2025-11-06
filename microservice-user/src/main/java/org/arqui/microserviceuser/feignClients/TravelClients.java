package org.arqui.microserviceuser.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "microservice-travel", url = "http://localhost:8084")
public interface TravelClients {
    @GetMapping("/api/travel/fechaInicio/{inicio}/fechaFin/{fin}")
    public List<Long> obtenerIdUsuariosConMasViajes(
            @PathVariable LocalDate fechaInicio,
            @PathVariable LocalDate fechaFin);

}
