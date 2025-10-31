package org.arqui.microserviceuser.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceuser.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;
}
