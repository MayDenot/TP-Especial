package org.arqui.microserviceuser.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceuser.entity.Account;
import org.arqui.microserviceuser.mapper.AccountMapper;
import org.arqui.microserviceuser.repository.AccountRepository;
import org.arqui.microserviceuser.service.DTO.request.AccountRequestDTO;
import org.arqui.microserviceuser.service.DTO.response.AccountResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponseDTO save(AccountRequestDTO account) {
        Account cuenta= AccountMapper.toEntity(account);
        Account nuevaCuenta=accountRepository.save(cuenta);
        return AccountMapper.toResponse(nuevaCuenta);
    }

    @Transactional
    public AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO) {
        Account cuenta = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no registrada con id: " + id));

        cuenta.setFechaDeAlta(accountRequestDTO.getFechaDeAlta());
        cuenta.setMontoDisponible(accountRequestDTO.getMontoDisponible());
        cuenta.setActiva(accountRequestDTO.isActiva());
        cuenta.setTipoCuenta(accountRequestDTO.getTipoCuenta());

        Account cuentaActualizada = accountRepository.save(cuenta);
        return AccountMapper.toResponse(cuentaActualizada);
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrado con id: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO findById(Long id) {
        Account cuenta = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrado con id: " + id));
        return AccountMapper.toResponse(cuenta);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> findAll() {
        return this.accountRepository.findAll()
                .stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    // Anular/Desactivar cuenta
    @Transactional
    public AccountResponseDTO anularCuenta(Long id) {
        Account cuenta = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));

        cuenta.setActiva(false);
        Account cuentaAnulada = accountRepository.save(cuenta);
        return AccountMapper.toResponse(cuentaAnulada);
    }

}
