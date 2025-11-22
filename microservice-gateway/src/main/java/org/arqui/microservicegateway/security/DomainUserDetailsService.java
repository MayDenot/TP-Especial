package org.arqui.microservicegateway.security;

import org.arqui.microservicegateway.entity.Autority;
import org.arqui.microservicegateway.entity.User;
import org.arqui.microservicegateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username ) {
        log.info("Cargando usuario: {}", username);
        log.info("Buscando en BD con username en minúsculas: {}", username.toLowerCase());

        return userRepository
                .findOneWithAuthoritiesByUsernameIgnoreCase( username.toLowerCase() )
                .map( user -> {
                    log.info("Usuario encontrado: {}, Autoridades: {}", user.getUsername(), user.getAuthorities().size());
                    return this.createSpringSecurityUser(user);
                })
                .orElseThrow( () -> {
                    log.error("Usuario no encontrado: {}", username);
                    return new UsernameNotFoundException( "El usuario " + username + " no existe" );
                });
    }

    private UserDetails createSpringSecurityUser( User user ) {
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map( Autority::getName )
                .map( SimpleGrantedAuthority::new )
                .collect( Collectors.toList() );

        return new org.springframework.security.core.userdetails.User( user.getUsername(), user.getPassword(), grantedAuthorities );
    }

}
