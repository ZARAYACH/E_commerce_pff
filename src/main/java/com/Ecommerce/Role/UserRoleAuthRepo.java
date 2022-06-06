package com.Ecommerce.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRoleAuthRepo extends JpaRepository<UserRoleAuth,Long> {

    @Query("SELECT r FROM UserRoleAuth r where r.name =:name")
    Collection<UserRoleAuth> getUserRoleAuthByName(@Param("name") String name);

}
