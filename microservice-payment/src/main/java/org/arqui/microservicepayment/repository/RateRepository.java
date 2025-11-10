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

  @Query("""
      SELECT r
      FROM Rate r
      WHERE r.fechaActualizacion = (
            SELECT MAX(r2.fechaActualizacion)
            FROM Rate r2
            WHERE r2.fechaActualizacion <= :fechaViaje
      )
  """)
  Optional<Rate> findRateByDate(LocalDateTime fechaViaje);
}
