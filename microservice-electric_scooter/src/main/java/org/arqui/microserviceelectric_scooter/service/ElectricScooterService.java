package org.arqui.microserviceelectric_scooter.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.EstadoScooter;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.feignClient.TravelCliente;
import org.arqui.microserviceelectric_scooter.mapeador.ElectricScooterMapper;
import org.arqui.microserviceelectric_scooter.repository.ElectricScooterRepository;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ReporteUsoScooterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ElectricScooterService {

    private final TravelCliente travelClient;
    private final ElectricScooterRepository repository;


    public ElectricScooterService(TravelCliente travelClient, ElectricScooterRepository repository) {
        this.travelClient = travelClient;
        this.repository = repository;
    }

    @Transactional
    public void save(ElectricScooterRequestDTO electricScooter) {
        ElectricScooter entity = ElectricScooterMapper.toEntity(electricScooter);
        entity.onCreate();
        repository.save(entity);
    }

    @Transactional
    public void modifier(String id, ElectricScooterRequestDTO electricScooterDTO) {
        ElectricScooter existing = (ElectricScooter) this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Electric Scooter no encontrado con id: " + id));

        // Actualizar solo los campos que no son nulos en el DTO
        if (electricScooterDTO.getLongitud() != null) {
            existing.setLongitud(electricScooterDTO.getLongitud());
        }

        if (electricScooterDTO.getLatitud() != null) {
            existing.setLatitud(electricScooterDTO.getLatitud());
        }

        if (electricScooterDTO.getHabilitado() != null) {
            existing.setHabilitado(electricScooterDTO.getHabilitado());
        }

        if (electricScooterDTO.getBateria() != null) {
            existing.setBateria(electricScooterDTO.getBateria());
        }

        if (electricScooterDTO.getTiempoDeUso() != null) {
            existing.setTiempoDeUso(electricScooterDTO.getTiempoDeUso());
        }

        if (electricScooterDTO.getKilometrosRecorridos() != null) {
            existing.setKilometrosRecorridos(electricScooterDTO.getKilometrosRecorridos());
        }

        if (electricScooterDTO.getEnParada() != null) {
            existing.setEnParada(electricScooterDTO.getEnParada());
        }

        if (electricScooterDTO.getCodigoQR() != null && !electricScooterDTO.getCodigoQR().isBlank()) {
            existing.setCodigoQR(electricScooterDTO.getCodigoQR());
        }

        if (electricScooterDTO.getEstado() != null) {
            try {
                EstadoScooter estadoEnum = EstadoScooter.valueOf(String.valueOf(electricScooterDTO.getEstado()));
                existing.setEstado(estadoEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Estado inválido: '" + electricScooterDTO.getEstado() + "'. Valores permitidos: " +
                                Arrays.toString(EstadoScooter.values())
                );
            }
        }

        if (electricScooterDTO.getIdParadaActual() != null) {
            existing.setIdParadaActual(electricScooterDTO.getIdParadaActual());
        }

        // Actualizar la fecha de última actualización siempre
        existing.setUltimaActualizacion(LocalDateTime.now());

        // NOTA: NO actualizamos 'id' ni 'fechaAlta' porque no deben cambiar

        // Guardar
        ElectricScooter updated = this.repository.save(existing);
    }

    @Transactional
    public void deleteById(String id) {
        if (!this.repository.existsById(id)) {
            throw new RuntimeException("Electric Scooter no encontrado con id: " + id); // ✅ LANZA LA EXCEPCIÓN
        }
        this.repository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public ElectricScooterResponseDTO getById(String id) {
        ElectricScooter scooter = this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Electric Scooter no encontrado con id: " + id));

        return ElectricScooterMapper.toResponse(scooter);
    }

    @Transactional(readOnly = true)
    public List<ElectricScooterResponseDTO> obtenerMonopatinesByAnioYCantidad(Integer cantidad, Integer anio) {
        List<String> ids = travelClient.obtenerIdsMonopatines(cantidad, anio);
        List<ElectricScooterResponseDTO> resultado = new ArrayList<>();

        for (String id : ids) {
            repository.findById(id).ifPresent(entity -> {
                resultado.add(ElectricScooterMapper.toResponse(entity)); // ✅ usamos el mapper
            });
        }

        return resultado;
    }

    @Transactional(readOnly = true)
    public List<ElectricScooterResponseDTO> obtenerCercanos(Double latitud, Double longitud) {
        double radioMetros = 2000; // 2 km de radio
        List<ElectricScooter> resultado = repository.buscarPorZona(latitud, longitud, radioMetros);

        // ❌ NO lances excepción si está vacío, simplemente retorna lista vacía
        // El controller maneja la respuesta apropiada

        return resultado.stream()
                .map(ElectricScooterMapper::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<ReporteUsoScooterDTO> generarReporteUso(boolean incluirPausas) {
        List<ElectricScooter> scooters = repository.findAll();

        return scooters.stream()
                .map(scooter -> {
                    ReporteUsoScooterDTO dto = ElectricScooterMapper.toReporte(scooter);

                    if (!incluirPausas) {
                        // Excluir las pausas del total de uso
                        long tiempoSoloMovimiento = dto.getTiempoTotalUsoSegundos() - dto.getTiempoEnPausaSegundos();
                        dto.setTiempoTotalUsoSegundos(Math.max(tiempoSoloMovimiento, 0));
                    }

                    return dto;
                })
                .toList();
    }



    @Transactional
    public ElectricScooterResponseDTO actualizarEstadoEnParada(
            String id,
            String estado,
            Long idParadaActual,
            Double latitud,
            Double longitud) {

        // Validar que el estado no esté vacío si viene
        if (estado != null && estado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }

        // Validar que al menos un campo venga para actualizar
        if (estado == null && idParadaActual == null && latitud == null && longitud == null) {
            throw new IllegalArgumentException("Debe proporcionar al menos un campo para actualizar");
        }

        // Validar que si viene latitud, también venga longitud (y viceversa)
        if ((latitud != null && longitud == null) || (latitud == null && longitud != null)) {
            throw new IllegalArgumentException("Latitud y longitud deben enviarse juntas");
        }

        // Buscar el scooter existente
        ElectricScooter scooter = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Electric Scooter no encontrado con id: " + id));

        // Actualizar solo los campos que no son nulos
        if (estado != null && !estado.isBlank()) {
            try {
                EstadoScooter estadoEnum = EstadoScooter.valueOf(estado.toUpperCase());
                scooter.setEstado(estadoEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Estado inválido: '" + estado + "'. Valores permitidos: " +
                                Arrays.toString(EstadoScooter.values())
                );
            }
        }

        if (idParadaActual != null) {
            scooter.setIdParadaActual(idParadaActual);
        }

        if (latitud != null && longitud != null) {
            scooter.setLatitud(latitud);
            scooter.setLongitud(longitud);
        }

        // Guardar los cambios
        ElectricScooter scooterActualizado = repository.save(scooter);

        // Convertir a DTO y retornar
        return ElectricScooterMapper.toResponse(scooterActualizado);
    }
}