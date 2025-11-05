package org.arqui.microserviceuser.repository;

import org.arqui.microserviceuser.entity.Account;
import org.arqui.microserviceuser.service.DTO.response.AccountResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

}
