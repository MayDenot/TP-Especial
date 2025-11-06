package org.arqui.microservicetravel.service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoResponseDTO {
    private Long id_account;
    private String tipoCuenta; // "PREMIUM" o "BASIC"
    private boolean activa;
}
