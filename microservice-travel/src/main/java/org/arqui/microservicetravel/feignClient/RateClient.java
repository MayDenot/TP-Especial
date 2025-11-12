package org.arqui.microservicetravel.feignClient;

import org.arqui.microservicetravel.service.DTO.Response.RateInfoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "microservice-payment", url = "http://localhost:8083")
public interface RateClient {

    @GetMapping("/api/rates/byDate")
    RateInfoResponseDTO getRateByDate(@RequestParam LocalDateTime fecha);
}