package org.arqui.microserviceuser.repository;

import org.arqui.microserviceuser.entity.Account;
import org.arqui.microserviceuser.service.DTO.response.AccountResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("""
        SELECT a
        FROM Account a
        JOIN a.usuarios u
        WHERE u.id_user = :userId
        AND a.activa = true
        ORDER BY a.fechaDeAlta DESC
    """)
    Optional<Account> findFirstActiveAccountByUserId(@Param("userId") Long userId);

}
