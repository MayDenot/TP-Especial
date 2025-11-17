package org.arqui.microservicegateway.service.dto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicegateway.entity.User;
import org.arqui.microservicegateway.repository.AutorityRepository;
import org.arqui.microservicegateway.repository.UserRepository;
import org.arqui.microservicegateway.service.dto.user.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AutorityRepository authorityRepository;

    public long saveUser( UserDTO request ) {
        final var user = new User( request.getUsername() );
        user.setPassword( passwordEncoder.encode( request.getPassword() ) );
        final var roles =  this.authorityRepository.findAllById( request.getAuthorities() );
        user.setAuthorities( roles );
        final var result = this.userRepository.save( user );
        return result.getId();
    }
}

