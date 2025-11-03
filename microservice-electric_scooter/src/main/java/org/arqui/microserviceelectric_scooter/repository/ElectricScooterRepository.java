package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;


public interface ElectricScooterRepository extends MongoRepository<ElectricScooter, String> {
}
