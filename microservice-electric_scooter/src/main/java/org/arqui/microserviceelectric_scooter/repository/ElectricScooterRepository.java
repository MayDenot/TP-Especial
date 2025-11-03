package org.arqui.microserviceelectric_scooter.repository;

import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricScooterRepository extends JpaRepository<ElectricScooter, Long> {
}
