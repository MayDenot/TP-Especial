package org.arqui.microservicegateway.config;

import org.arqui.microservicegateway.security.AutorityConstant;
import org.arqui.microservicegateway.security.jwt.JwtFilter;
import org.arqui.microservicegateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public SecurityConfig( TokenProvider tokenProvider, UserDetailsService userDetailsService ) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http ) throws Exception {
        http
                .csrf( AbstractHttpConfigurer::disable );
        http
                .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
        http
                .securityMatcher("/api/**" )
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll() //autenticar gateway
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority(AutorityConstant._ADMIN)//get usuario
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority(AutorityConstant._ADMIN) //put usuario
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority(AutorityConstant._ADMIN) //delete usuario
                        .requestMatchers(HttpMethod.GET,
                                "/api/users/fechaInicio/**/fechaFin/**/tipoCuenta/**").hasAuthority(AutorityConstant._ADMIN) // get manopatínes mas usados
                        .requestMatchers("/api/users/cercanos/{latitud}/{longitud}").hasAuthority(AutorityConstant._USER) // get monopatínes cercanos
                        .requestMatchers("/api/users/*/usos").hasAuthority(AutorityConstant._USER) // get usos
                        .requestMatchers( "/api/scooters/**").hasAuthority( AutorityConstant._ADMIN )
                        .requestMatchers(HttpMethod.PUT, "/api/accounts/{id}/anular").hasAuthority( AutorityConstant._ADMIN )
                        .requestMatchers("/api/accounts/**").hasAuthority(AutorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers("/api/rates/**").hasAuthority (AutorityConstant._ADMIN)
                        .requestMatchers("/api/travels/**").hasAuthority (AutorityConstant._ADMIN)
                        .requestMatchers("/api/stops/**").hasAuthority (AutorityConstant._ADMIN)


                )
                .httpBasic( Customizer.withDefaults() )
                .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

}
