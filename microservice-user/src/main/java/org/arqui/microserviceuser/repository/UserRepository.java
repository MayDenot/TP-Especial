package org.arqui.microserviceuser.repository;

import org.arqui.microserviceuser.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
