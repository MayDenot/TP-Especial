package org.arqui.microserviceuser.controller;

import org.arqui.microserviceuser.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
}
