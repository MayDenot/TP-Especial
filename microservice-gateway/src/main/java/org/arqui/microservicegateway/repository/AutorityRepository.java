package org.arqui.microservicegateway.repository;

import org.arqui.microservicegateway.entity.Autority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorityRepository extends JpaRepository<Autority, String> {

}
