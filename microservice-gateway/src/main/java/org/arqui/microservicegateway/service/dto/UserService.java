package org.arqui.microservicegateway.service.dto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicegateway.entity.User;
import org.arqui.microservicegateway.repository.AutorityRepository;
import org.arqui.microservicegateway.repository.UserRepository;
import org.arqui.microservicegateway.service.dto.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AutorityRepository authorityRepository;

    public long saveUser( UserDTO request ) {
        log.info("Creando usuario: {}", request.getUsername());
        log.info("Autoridades solicitadas: {}", request.getAuthorities());

        final var user = new User( request.getUsername() );
        final var encodedPassword = passwordEncoder.encode( request.getPassword() );
        user.setPassword( encodedPassword );

        log.info("Contraseña encriptada. Hash: {}", encodedPassword.substring(0, Math.min(20, encodedPassword.length())) + "...");

        final var roles =  this.authorityRepository.findAllById( request.getAuthorities() );
        log.info("Autoridades encontradas en BD: {}", roles.size());

        if (roles.isEmpty()) {
            log.error("No se encontraron autoridades para: {}", request.getAuthorities());
            throw new RuntimeException("No se encontraron las autoridades especificadas");
        }

        user.setAuthorities( roles );
        final var result = this.userRepository.save( user );

        log.info("Usuario creado exitosamente con ID: {}, Username: {}", result.getId(), result.getUsername());
        log.info("Autoridades asignadas: {}", result.getAuthorities());

        return result.getId();
    }
}

