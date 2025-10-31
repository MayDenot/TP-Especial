package org.arqui.microserviceuser.controller;

import org.arqui.microserviceuser.service.AccountService;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private AccountService accountService;
}
