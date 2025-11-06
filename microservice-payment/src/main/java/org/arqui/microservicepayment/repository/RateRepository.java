package org.arqui.microservicepayment.repository;

import org.arqui.microservicepayment.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
  @Modifying
  @Query("UPDATE Rate r SET r.vigente = true WHERE r.fecha <= :fecha AND r.vigente = false")
  void habilitarNuevosPreciosAPartirDe(LocalDateTime fecha);

  @Query("""
      SELECT r
      FROM Rate r
      WHERE r.vigente = true
      AND r.fecha <= :fecha
      ORDER BY r.fecha DESC
      LIMIT 1
  """)
  Optional<Rate> findRateByDate(LocalDateTime fecha);
}
