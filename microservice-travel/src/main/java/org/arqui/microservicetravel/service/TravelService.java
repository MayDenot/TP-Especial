package org.arqui.microservicetravel.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.Mapper.TravelMapper;
import org.arqui.microservicetravel.entity.Travel;
import org.arqui.microservicetravel.feignClient.AccountClient;
import org.arqui.microservicetravel.feignClient.ElectricScooterClient;
import org.arqui.microservicetravel.feignClient.RateClient;
import org.arqui.microservicetravel.repository.TravelRepository;
import org.arqui.microservicetravel.service.DTO.Request.ActualizarEstadoParadaRequestDTO;
import org.arqui.microservicetravel.service.DTO.Request.FinalizarViajeRequestDTO;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.AccountInfoResponseDTO;
import org.arqui.microservicetravel.service.DTO.Response.RateInfoResponseDTO;
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
    private final RateClient rateFeignClient;
    private final AccountClient accountFeignClient;
    private final ElectricScooterClient electricScooterClient;

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
        travel.setParada_inicio(travelRequestDTO.getParada_inicio());
        travel.setParada_fin(travelRequestDTO.getParada_fin());
        travel.setMonopatin(travelRequestDTO.getMonopatin());
        travel.setUsuario(travelRequestDTO.getUsuario());
        travel.setCosto(travelRequestDTO.getCosto());

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
    public List<TravelResponseDTO> obtenerViajesPorUsuario(Long userId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Travel> viajes = travelRepository.findByUsuarioAndFechaBetween(userId, fechaInicio, fechaFin);

        return viajes.stream().map(TravelMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TravelResponseDTO finalizarViaje(Long travelId, FinalizarViajeRequestDTO request) {
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + travelId));

        if (travel.getFecha_hora_fin() != null) {
            throw new RuntimeException("El viaje ya fue finalizado");
        }


        travel.setParada_fin(request.getParadaFinId());
            travel.setFecha_hora_fin(LocalDateTime.now());

            // =========================
            // CÁLCULO DE TARIFA Y COSTO
            // =========================

            // 1) Obtener tarifa vigente según la fecha de inicio del viaje
            if (travel.getFecha_hora_inicio() == null) {
                throw new RuntimeException("El viaje con ID " + travel.getId_travel() + " no tiene fecha de inicio");
            }

            RateInfoResponseDTO rate;
            try {
                String fechaISO = travel.getFecha_hora_fin().toString();
                rate = rateFeignClient.getRateByDate(fechaISO);
                if (rate == null) {
                    throw new RuntimeException("No se encontró tarifa para la fecha: " + travel.getFecha_hora_inicio());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al obtener tarifa para viaje " + travel.getId_travel() +
                        " con fecha " + travel.getFecha_hora_inicio() + ": " + e.getMessage(), e);
            }

            // 2) Obtener tipo de cuenta del usuario (se usa para posibles reglas de negocio futuras)
            AccountInfoResponseDTO account;
            try {
                account = accountFeignClient.getAccountByUserId(travel.getUsuario());
                if (account == null) {
                    account = new AccountInfoResponseDTO();
                    account.setTipoCuenta("REGULAR");
                }
            } catch (Exception e) {
                // Fallback a REGULAR si falla el microservicio de cuentas
                account = new AccountInfoResponseDTO();
                account.setTipoCuenta("REGULAR");
            }

            // 3) Calcular costo base y costo extra por pausas
            Double tarifaACobrar = 0.0;
            if (travelRepository.duracionDePausas(travelId) >= 15)
                tarifaACobrar = rate.getTarifaExtra();
            tarifaACobrar = rate.getTarifa();

            travel.setCosto(tarifaACobrar * travel.getKmRecorridos());

            try {
                ActualizarEstadoParadaRequestDTO scooterRequest = new ActualizarEstadoParadaRequestDTO(
                        "DISPONIBLE",
                        request.getParadaFinId(),
                        request.getLatitud(),
                        request.getLongitud()
                );

                electricScooterClient.actualizarEstadoEnParada(
                        travel.getMonopatin(),
                        scooterRequest
                );

                System.out.println("Monopatín " + travel.getMonopatin() +
                        " actualizado a DISPONIBLE en parada " + request.getParadaFinId());

            } catch (Exception e) {
                System.err.println("Error al actualizar estado del monopatín: " + e.getMessage());
            }

            travelRepository.save(travel);

            return TravelMapper.toResponse(travel);
        }


    public List<TravelResponseDTO> viajesFinalizados(Integer anio, Integer mesInicio, Integer mesFin) {
        List<Travel> viajesTerminados = travelRepository.getViajesFinalizados(anio,mesInicio,mesFin);
        return viajesTerminados.stream()
                .map(TravelMapper::toResponse)
                .toList();
    }

}
