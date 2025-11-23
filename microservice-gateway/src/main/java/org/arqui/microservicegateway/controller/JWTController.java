package org.arqui.microservicegateway.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticación", description = "Gestión de autenticación y tokens JWT")
public class JWTController {

    private static final Logger log = LoggerFactory.getLogger(JWTController.class);

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Autenticar usuario",
            description = "Valida las credenciales del usuario y genera un token JWT para acceder a los recursos protegidos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticación exitosa - Token JWT generado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JWTToken.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales inválidas"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos"
            )
    })

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


    @Schema(description = "Respuesta con el token JWT")
    static class JWTToken {
        @Schema(description = "Token JWT para autenticación", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
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
