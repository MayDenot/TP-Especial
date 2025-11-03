package org.arqui.microservicetravel.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.Mapper.TravelMapper;
import org.arqui.microservicetravel.entity.Travel;
import org.arqui.microservicetravel.repository.TravelRepository;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {
    private TravelRepository travelRepository;

    public void save(TravelRequestDTO travel){
        travelRepository.save(TravelMapper.toEntity(travel));
    }

    public void delete(Long id){
        travelRepository.deleteById(id);
    }

    public List<String> buscarPorCantidadDeViajesYAño(Integer cantidadViajes, Integer anio) {
        return travelRepository.buscarPorCantidadDeViajesYAño(cantidadViajes, anio);
    }
}
