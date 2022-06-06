package com.Ecommerce.Cart;

import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {

    @Query(value = "select * from cart where id = :id",nativeQuery = true)
    Cart findCartById(@Param("id") Long id);

}
