package com.Ecommerce.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRoleAuthRepo extends JpaRepository<UserRoleAuth,Long> {

    Collection<UserRoleAuth> getUserRoleAuthByName(String name);

}
