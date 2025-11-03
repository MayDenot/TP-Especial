package org.arqui.microservicepayment.repository;

import org.arqui.microservicepayment.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

  @Query("UPDATE Rate r SET r.vigente = true WHERE r.fecha <= :fecha AND r.vigente = false")
  void habilitarNuevosPreciosAPartirDe(LocalDateTime fecha);
}
