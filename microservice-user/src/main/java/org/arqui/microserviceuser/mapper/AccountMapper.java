package org.arqui.microserviceuser.mapper;

import org.arqui.microserviceuser.entity.Account;
import org.arqui.microserviceuser.service.DTO.request.AccountRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.AccountResponseDTO;

public class AccountMapper {

    public static Account toEntity(AccountRequestDTO accountRequestDTO) {
        Account account= new Account();
        account.setFechaDeAlta(accountRequestDTO.getFechaDeAlta());
        account.setMontoDisponible(accountRequestDTO.getMontoDisponible());
        account.setActiva(accountRequestDTO.isActiva());
        account.setTipoCuenta(accountRequestDTO.getTipoCuenta());

        return account;
    }

    public static AccountResponseDTO toResponse (Account account) {
        return new AccountResponseDTO(
                account.getId_account(),
                account.getFechaDeAlta(),
                account.getMontoDisponible(),
                account.isActiva(),
                account.getTipoCuenta()
        );
    }
}
