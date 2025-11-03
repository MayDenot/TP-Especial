package org.arqui.microservicepayment.repository;

import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>{
}
