package org.arqui.microservicetravel.feignClient;

import org.arqui.microservicetravel.service.DTO.Response.AccountInfoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-user")
public interface AccountClient {

    @GetMapping("/api/accounts/usuario/{userId}")
    AccountInfoResponseDTO getAccountByUserId(@PathVariable Long userId);
}