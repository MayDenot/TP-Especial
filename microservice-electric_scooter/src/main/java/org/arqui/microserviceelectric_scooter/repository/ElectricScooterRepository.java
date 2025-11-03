package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface ElectricScooterRepository extends MongoRepository<ElectricScooterRepository, String> {




    void deleteById(String id);
    void save(ElectricScooter electricScooter);
    void modifier(ElectricScooterRequestDTO electricScooter);




}
