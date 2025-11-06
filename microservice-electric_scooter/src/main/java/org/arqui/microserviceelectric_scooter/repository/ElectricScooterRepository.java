package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricScooterRepository extends MongoRepository<ElectricScooter, String> {


    @Query("{ 'ubicacion': { $near: { $geometry: { type: 'Point', coordinates: [ ?1, ?0 ] }, $maxDistance: ?2 } } }")
    List<ElectricScooter> buscarPorZona(Double latitud, Double longitud, Double maxDistance);


    //metodos abm ya vienen po defecto con mongoDbRespository


}
