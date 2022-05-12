package com.Ecommerce.User;

import com.Ecommerce.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User getUserByEmail(String email);
    @Query(value = "select * from user",nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "update user set is_active=false where id =:id",nativeQuery = true)
    void suspendUser(Long id);

    @Query(value = "update user set is_active=true where id =:id",nativeQuery = true)
    void unSuspendUser(Long id);
    @Query(value = "select u from user u where u.email = :email")
    User existsByEmail(String email);
}
