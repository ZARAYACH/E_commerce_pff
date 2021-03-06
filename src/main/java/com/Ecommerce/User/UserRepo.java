package com.Ecommerce.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    @Query("select u from user u where u.email=:email")
    User findUserByEmail(@Param("email") String email);
    @Query(value = "select * from user",nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "update user set is_active=false where id =:id",nativeQuery = true)
    void suspendUser(Long id);

    @Query(value = "update user set is_active=true where id =:id",nativeQuery = true)
    void unSuspendUser(Long id);
    @Query(value = "SELECT * FROM user WHERE email =:email", nativeQuery = true)
    User existsByEmail(@Param("email") String email) ;
    @Query(value = "select * from user where phone_number =:phoneNumber",nativeQuery = true)
    User existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(value = "select * from user where id = :userID",nativeQuery = true)
    User findUserById(@Param("userID") Long userID);


}
