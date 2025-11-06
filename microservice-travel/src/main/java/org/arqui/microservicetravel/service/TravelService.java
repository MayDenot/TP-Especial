package org.arqui.microservicetravel.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.Mapper.TravelMapper;
import org.arqui.microservicetravel.entity.Travel;
import org.arqui.microservicetravel.repository.TravelRepository;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.TravelResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    @Transactional
    public void save(TravelRequestDTO travel){
        travelRepository.save(TravelMapper.toEntity(travel));
    }

    @Transactional
    public void delete(Long id){
        travelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TravelResponseDTO findById(Long id){
        Travel travel = travelRepository.findById(id).orElseThrow(()->new RuntimeException("No existe viaje con ese ID"));
        return TravelMapper.toResponse(travel);
    }

    @Transactional(readOnly = true)
    public List<TravelResponseDTO> findAll(){
        List<Travel> travels = travelRepository.findAll();
        return travels.stream().map(TravelMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TravelResponseDTO update(Long id, TravelRequestDTO travelRequestDTO){
        Travel travel = travelRepository.findById(id).orElseThrow(()->new RuntimeException("No existe viaje con ese ID"));
        travel.setFecha_hora_inicio(travelRequestDTO.getFecha_hora_inicio());
        travel.setFecha_hora_fin(travelRequestDTO.getFecha_hora_fin());
        travel.setKmRecorridos(travelRequestDTO.getKmRecorridos());
        travel.setPausado(travelRequestDTO.getPausado());
        travel.setTarifa(travelRequestDTO.getTarifa());
        travel.setParada_inicio(travelRequestDTO.getParada_inicio());
        travel.setParada_fin(travelRequestDTO.getParada_fin());
        travel.setMonopatin(travelRequestDTO.getMonopatin());
        travel.setUsuario(travelRequestDTO.getUsuario());

        return TravelMapper.toResponse(travelRepository.save(travel));
    }

    @Transactional(readOnly = true)
    public List<String> buscarPorCantidadDeViajesYAño(Integer cantidadViajes, Integer anio) {
        return travelRepository.buscarPorCantidadDeViajesYAño(cantidadViajes, anio);
    }

    @Transactional(readOnly = true)
    public List<Long> buscarUsuariosConMasViajes(LocalDate inicio, LocalDate fin) {
        return travelRepository.buscarUsuariosConMasViajes(inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<Long> buscarTarifas(LocalDateTime inicio, LocalDateTime fin) {
        return travelRepository.buscarTarifas(inicio, fin);
    }
}
