package com.Ecommerce.UserCredientiels;

import com.Ecommerce.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepo extends JpaRepository<UserCredentials,Long> {

}
