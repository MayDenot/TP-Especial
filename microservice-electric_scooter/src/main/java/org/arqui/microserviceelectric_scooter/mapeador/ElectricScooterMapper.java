package org.arqui.microserviceelectric_scooter.mapeador;

import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceelectric_scooter.EstadoScooter;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ReporteUsoScooterDTO;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

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
        scooter.setEstado(EstadoScooter.valueOf(String.valueOf(dto.getEstado())));
        scooter.setIdParadaActual(dto.getIdParadaActual());

         scooter.actualizarUbicacion();

        return scooter;
    }


    public static ElectricScooterResponseDTO toResponse(ElectricScooter scooter) {
        return new ElectricScooterResponseDTO(
                scooter.get_id(),
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


    public static ReporteUsoScooterDTO toReporte(ElectricScooter scooter) {
        long total = scooter.getTiempoDeUso() != null ? scooter.getTiempoDeUso() : 0L;
        long tiempoEnMovimiento = scooter.getEnParada() ? 0L : total;
        long tiempoEnPausa = scooter.getEnParada() ? total : 0L;

        return new ReporteUsoScooterDTO(
                scooter.get_id(),
                scooter.getKilometrosRecorridos(),
                total,
                tiempoEnMovimiento,
                tiempoEnPausa,
                scooter.getBateria() != null ? scooter.getBateria().doubleValue() : 0.0,
                scooter.getEstado() != null ? scooter.getEstado().name() : "DESCONOCIDO"
        );
    }



    public static List<ReporteUsoScooterDTO> toReporteBasico(List<ElectricScooter> scooters) {
        return scooters.stream()
                .map(ElectricScooterMapper::toReporte)
                .toList();
    }

}
