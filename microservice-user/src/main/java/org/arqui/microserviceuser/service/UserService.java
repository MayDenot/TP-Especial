package org.arqui.microserviceuser.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceuser.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
}
