package org.arqui.microserviceuser.service;

import org.arqui.microserviceuser.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
}
