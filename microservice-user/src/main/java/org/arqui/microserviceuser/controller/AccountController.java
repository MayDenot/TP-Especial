package org.arqui.microserviceuser.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceuser.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private AccountService accountService;
}
