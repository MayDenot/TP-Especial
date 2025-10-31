package org.arqui.microserviceuser.repository;

import org.arqui.microserviceuser.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
}
