package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricScooterRepository extends MongoRepository<ElectricScooter, String> {






    List<ElectricScooterResponseDTO> buscarPorZona(Double latitud, Double longitud);


    //metodos abm ya vienen po defecto con mongoDbRespository


}
