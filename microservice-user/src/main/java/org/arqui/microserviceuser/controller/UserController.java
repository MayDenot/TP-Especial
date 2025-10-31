package org.arqui.microserviceuser.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceuser.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
}
