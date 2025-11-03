package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ElectricScooterRepository extends MongoRepository<ElectricScooterRepository, String> {

    void deleteById(String id);
    void save(ElectricScooterRequestDTO electricScooter);




}
