package org.example.microservicestop.service;

import lombok.RequiredArgsConstructor;
import org.example.microservicestop.Mapper.StopMapper;
import org.example.microservicestop.entity.Stop;
import org.example.microservicestop.repository.StopRepository;
import org.example.microservicestop.service.DTO.Request.StopRequestDTO;
import org.example.microservicestop.service.DTO.Response.StopResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StopService {
    private StopRepository stopRepository;

    @Transactional
    public void save(StopRequestDTO stop){
        stopRepository.save(StopMapper.toEntity(stop));
    }
    @Transactional
    public StopResponseDTO delete(Long id){
        return StopMapper.toResponse(stopRepository.findById(id).
                orElseThrow(() -> new RuntimeException("No existe")));
    }
    @Transactional
    public StopResponseDTO update(Long id, StopRequestDTO stop){
        Stop stopEntity = stopRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe"));
        stopEntity.setNombre(stop.getNombre());
        stopEntity.setDireccion(stop.getDireccion());
        stopEntity.setLatitud(stop.getLatitud());
        stopEntity.setLongitud(stop.getLongitud());
        return StopMapper.toResponse(stopRepository.save(stopEntity));
    }
}
