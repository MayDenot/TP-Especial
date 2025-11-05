package org.arqui.microserviceuser.repository;

import org.arqui.microserviceuser.Rol;
import org.arqui.microserviceuser.entity.User;
import org.arqui.microserviceuser.service.DTO.response.UserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRol(Rol rol);
}
