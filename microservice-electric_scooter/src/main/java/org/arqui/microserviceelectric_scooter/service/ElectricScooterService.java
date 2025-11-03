package org.arqui.microserviceelectric_scooter.service;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.mapeador.ElectricScooterMapper;
import org.arqui.microserviceelectric_scooter.repository.ElectricScooterRepository;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;

public class ElectricScooterService {


    private ElectricScooterRepository repository;


    public ElectricScooterService(ElectricScooterRepository repository) {
        this.repository = repository;
    }

    public void save(ElectricScooterRequestDTO electricScooter) {
        ElectricScooter entity = ElectricScooterMapper.toEntity(electricScooter);
        repository.save(entity);
    }

    public void modifier(ElectricScooterRequestDTO electricScooter) {
        ElectricScooter entity = ElectricScooterMapper.toEntity(electricScooter);

    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

}
