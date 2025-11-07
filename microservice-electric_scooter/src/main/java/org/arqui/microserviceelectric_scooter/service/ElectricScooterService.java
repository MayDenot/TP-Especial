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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .orElseThrow(() -> new RuntimeException("Electric Scooter no encontrado con : " + id));

        // 2. Actualizar solo los campos que vienen en el DTO
        existing.setLongitud(electricScooterDTO.getLongitud());
        existing.setLatitud(electricScooterDTO.getLatitud());
        existing.setHabilitado(electricScooterDTO.getHabilitado());
        existing.setBateria(electricScooterDTO.getBateria());
        existing.setTiempoDeUso(electricScooterDTO.getTiempoDeUso());
        existing.setKilometrosRecorridos(electricScooterDTO.getKilometrosRecorridos());
        existing.setEnParada(electricScooterDTO.getEnParada());
        existing.setCodigoQR(electricScooterDTO.getCodigoQR());
        existing.setEstado(EstadoScooter.valueOf(electricScooterDTO.getEstado()));
        existing.setIdParadaActual(electricScooterDTO.getIdParadaActual());

        // 3. Actualizar la fecha de última actualización
        existing.setUltimaActualizacion(LocalDateTime.now());

        // NOTA: NO actualizamos 'id' ni 'fechaAlta' porque no deben cambiar

        // 4. Guardar
        ElectricScooter updated = this.repository.save(existing);
    }

    @Transactional
    public void deleteById(String id) {
        if (!this.repository.existsById(id)) {
            throw new RuntimeException("Electric Scooter no encontrado con id: " + id); // ✅ LANZA LA EXCEPCIÓN
        }
        this.repository.deleteById(id);
    }


    @Transactional
    public ElectricScooterResponseDTO getById(String id) {
        ElectricScooter scooter = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Electric Scooter no encontrado con : " + id));

        return ElectricScooterMapper.toResponse(scooter);
    }


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
    public List<ReporteUsoScooterDTO> generarReporteUso() {
        List<ElectricScooter> resultado = repository.findAll();
        return ElectricScooterMapper.toReporteBasico(resultado);

    }

    public ElectricScooterResponseDTO actualizarEstadoEnParada(
            String id,
            String estado,
            Long idParadaActual,
            Double latitud,
            Double longitud) {


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
                .orElseThrow(() -> new RuntimeException("Scooter no encontrado con id: " + id));

        // Actualizar solo los campos que no son nulos

        scooter.setEstado(EstadoScooter.valueOf(estado));
        scooter.setIdParadaActual(idParadaActual);
        scooter.setLatitud(latitud);

        scooter.setLongitud(longitud);


        // Guardar los cambios
        ElectricScooter scooterActualizado = repository.save(scooter);

        // Convertir a DTO y retornar
        return ElectricScooterMapper.toResponse(scooterActualizado);
    }
}