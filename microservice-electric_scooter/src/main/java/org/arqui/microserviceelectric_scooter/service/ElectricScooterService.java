package org.arqui.microserviceelectric_scooter.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.EstadoScooter;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.mapeador.ElectricScooterMapper;
import org.arqui.microserviceelectric_scooter.repository.ElectricScooterRepository;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class ElectricScooterService {


    private final  ElectricScooterRepository repository;


    public ElectricScooterService(ElectricScooterRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(ElectricScooterRequestDTO electricScooter) {
        ElectricScooter entity = ElectricScooterMapper.toEntity(electricScooter);
        entity.onCreate();
        repository.save(entity);
    }

    @Transactional
    public void modifier( String id,ElectricScooterRequestDTO electricScooterDTO) {
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
        ElectricScooter scooter =  this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Electric Scooter no encontrado con : " + id));

        return ElectricScooterMapper.toResponse(scooter);
    }



}
