package com.Ecommerce.UserCredentiels;

import com.Ecommerce.Logs.Logs;
import com.Ecommerce.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserCredentialsRepo extends JpaRepository<UserCredentials,Integer> {

    UserCredentials findByEmail(String email);

    UserCredentials getByEmail(String email);

}
