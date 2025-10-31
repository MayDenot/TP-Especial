package org.arqui.microserviceuser.service;

import org.arqui.microserviceuser.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;
}
