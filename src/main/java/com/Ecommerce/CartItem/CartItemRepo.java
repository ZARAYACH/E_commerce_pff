package com.Ecommerce.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query(value = "select c from cart_item c where c.cart.id = :id")
    List<CartItem> findAllByCartId(@Param("id") Long id);
}
