package org.arqui.microserviceuser.repository;

import org.arqui.microserviceuser.Rol;
import org.arqui.microserviceuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRol(Rol rol);

    @Query("""
    SELECT DISTINCT u FROM User u
    JOIN u.cuentas c
    WHERE u.id_user IN :idsUsuarios
    AND UPPER(c.tipoCuenta) = UPPER(:tipoCuenta)
    """)
    List<User> findByIdsAndTipoCuenta(
            @Param("idsUsuarios") List<Long> idsUsuarios,
            @Param("tipoCuenta") String tipoCuenta);
}
