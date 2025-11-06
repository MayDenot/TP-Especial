package org.arqui.microserviceuser.feignClients;

import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name="microservice-electricScooter", url = "http://localhost:8082" )
public interface ElectricScooterClients {

    //g-Como usuario quiero encontrar los monopatines cercanos a mi zona
    @GetMapping("/api/scooters/cercanos/{latitud}/{longitud}")
    List<ElectricScooterResponseDTO> obtenerMonopatinesCercanos(@PathVariable Double latitud, @PathVariable Double longitud);
}
