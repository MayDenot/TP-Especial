package org.arqui.microservicegateway.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicegateway.security.jwt.JwtFilter;
import org.arqui.microservicegateway.security.jwt.TokenProvider;
import org.arqui.microservicegateway.service.dto.login.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate" )
@RequiredArgsConstructor
public class JWTController {

    private static final Logger log = LoggerFactory.getLogger(JWTController.class);

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO request ) {

        log.info("Intento de autenticación para el usuario: {}", request.getUsername());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        try {
            Authentication authentication = authenticationManager.authenticate( authenticationToken );
            log.info("Autenticación exitosa para el usuario: {}", request.getUsername());

            SecurityContextHolder.getContext().setAuthentication( authentication );
            final var jwt = tokenProvider.createToken( authentication );
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add( JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt );

            log.info("Token JWT generado exitosamente para el usuario: {}", request.getUsername());
            return new ResponseEntity<>( new JWTToken( jwt ), httpHeaders, HttpStatus.OK );
        } catch (Exception e) {
            log.error("Error en la autenticación para el usuario: {}. Error: {}", request.getUsername(), e.getMessage());
            throw e;
        }
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
