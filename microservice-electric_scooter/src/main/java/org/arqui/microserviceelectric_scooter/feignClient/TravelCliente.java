package org.arqui.microserviceelectric_scooter.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-travel", url = "http://localhost:8084")
public interface TravelCliente {


    @GetMapping("/api/travels/cantidad/{cantidad}/anio/{anio}")
    List<String> obtenerIdsMonopatines(
            @PathVariable Integer cantidad,
            @PathVariable Integer anio
    );

}
