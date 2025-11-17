package org.arqui.microservicetravel.Mapper;

import org.arqui.microservicetravel.entity.Travel;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.TravelResponseDTO;

public class TravelMapper {
    public static Travel toEntity(TravelRequestDTO dto) {
        Travel travel = new Travel();
        travel.setFecha_hora_inicio(dto.getFecha_hora_inicio());
        travel.setFecha_hora_fin(dto.getFecha_hora_fin());
        travel.setKmRecorridos(dto.getKmRecorridos());
        travel.setPausado(dto.getPausado());
        travel.setParada_inicio(dto.getParada_inicio());
        travel.setParada_fin(dto.getParada_fin());
        travel.setMonopatin(dto.getMonopatin());
        travel.setUsuario(dto.getUsuario());
        travel.setCosto(dto.getCosto());
        return travel;
    }

    public static TravelResponseDTO toResponse(Travel travel) {
        return new TravelResponseDTO(
                travel.getId_travel(),
                travel.getFecha_hora_inicio(),
                travel.getFecha_hora_fin(),
                travel.getKmRecorridos(),
                travel.getPausado(),
                travel.getPausas(),
                travel.getParada_inicio(),
                travel.getParada_fin(),
                travel.getMonopatin(),
                travel.getUsuario(),
                travel.getCosto()
        );
    }
}
