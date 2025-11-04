package org.example.microservicestop.Mapper;

import org.example.microservicestop.entity.Stop;
import org.example.microservicestop.service.DTO.Request.StopRequestDTO;
import org.example.microservicestop.service.DTO.Response.StopResponseDTO;

public class StopMapper {
    public static Stop toEntity(StopRequestDTO dto){
        Stop stop = new Stop();
        stop.setDireccion(dto.getDireccion());
        stop.setLatitud(dto.getLatitud());
        stop.setLongitud(dto.getLongitud());
        stop.setNombre(dto.getNombre());
        return stop;
    }

    public static StopResponseDTO toResponse(Stop stop){
        return new StopResponseDTO(stop.getId_stop(),
                                    stop.getDireccion(),
                                    stop.getNombre(),
                                    stop.getLatitud(),
                                    stop.getLongitud());
    }
}
