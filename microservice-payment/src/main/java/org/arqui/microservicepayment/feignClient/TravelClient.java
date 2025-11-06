package org.arqui.microservicepayment.feignClient;

import org.arqui.microservicepayment.service.DTO.request.TravelWithCostRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-travel", url = "http://localhost:8084")
public interface TravelClient {
  @GetMapping("/api/travels/travelsWithCosts")
  List<TravelWithCostRequestDTO> getViajesConCostos(
          @RequestParam Integer anio,
          @RequestParam Integer mesInicio,
          @RequestParam Integer mesFin);
}
