package org.arqui.microservicetravel.service.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la respuesta de la info de una cuanta")
public class AccountInfoResponseDTO {
    @Schema(description = "ID de la cuenta", example = "1")
    private Long id_account;
    @Schema(description = "Tipo de cuenta", example = "PREMIUM")
    private String tipoCuenta; // "PREMIUM" o "BASIC"
    @Schema(description = "Estado de la cuenta", example = "true")
    private boolean activa;
}
