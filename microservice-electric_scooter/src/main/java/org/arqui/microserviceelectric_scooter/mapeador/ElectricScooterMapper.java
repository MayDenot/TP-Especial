package org.arqui.microserviceelectric_scooter.mapeador;

import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceelectric_scooter.EstadoScooter;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;

public class ElectricScooterMapper {

    public static ElectricScooter toEntity(ElectricScooterRequestDTO dto) {
        ElectricScooter scooter = new ElectricScooter();
        scooter.setLongitud(dto.getLongitud());
        scooter.setLatitud(dto.getLatitud());
        scooter.setHabilitado(dto.getHabilitado());
        scooter.setBateria(dto.getBateria());
        scooter.setTiempoDeUso(dto.getTiempoDeUso());
        scooter.setKilometrosRecorridos(dto.getKilometrosRecorridos());
        scooter.setEnParada(dto.getEnParada());
        scooter.setCodigoQR(dto.getCodigoQR());
        scooter.setEstado(EstadoScooter.valueOf(dto.getEstado()));
        scooter.setIdParadaActual(dto.getIdParadaActual());
        return scooter;
    }

    public static ElectricScooterResponseDTO toResponse(ElectricScooter scooter) {
        return new ElectricScooterResponseDTO(
                scooter.getId(),
                scooter.getLongitud(),
                scooter.getLatitud(),
                scooter.getHabilitado(),
                scooter.getBateria(),
                scooter.getTiempoDeUso(),
                scooter.getKilometrosRecorridos(),
                scooter.getEnParada(),
                scooter.getCodigoQR(),
                scooter.getEstado().name(),
                scooter.getIdParadaActual(),
                scooter.getFechaAlta(),
                scooter.getUltimaActualizacion()
        );
    }
}
