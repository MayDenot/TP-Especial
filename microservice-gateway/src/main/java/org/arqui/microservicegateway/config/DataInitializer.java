package org.arqui.microservicegateway.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicegateway.entity.Autority;
import org.arqui.microservicegateway.repository.AutorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final AutorityRepository authorityRepository;

    @PostConstruct
    public void init() {
        try {
            log.info("Inicializando autoridades del sistema...");

            // Crear autoridad ADMIN si no existe
            if (!authorityRepository.existsById("ADMIN")) {
                authorityRepository.save(new Autority("ADMIN"));
                log.info("Autoridad 'ADMIN' creada exitosamente");
            }

            // Crear autoridad USER si no existe
            if (!authorityRepository.existsById("USER")) {
                authorityRepository.save(new Autority("USER"));
                log.info("Autoridad 'USER' creada exitosamente");
            }

            // Crear autoridad MAINTENANCE si no existe
            if (!authorityRepository.existsById("MAINTENANCE")) {
                authorityRepository.save(new Autority("MAINTENANCE"));
                log.info("Autoridad 'MAINTENANCE' creada exitosamente");
            }

            log.info("Inicialización de autoridades completada");

        } catch (Exception e) {
            log.error("Error al inicializar autoridades: {}", e.getMessage(), e);
        }
    }
}

