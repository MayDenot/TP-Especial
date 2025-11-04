package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricScooterRepository extends MongoRepository<ElectricScooter, String> {




    //metodos abm ya vienen po defecto con mongoDbRespository


}
