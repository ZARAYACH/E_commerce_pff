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

    @Query(value = "delete from cart_item where cart_id = :id",nativeQuery = true)
    void deleteAllByCartId(Long id);

    @Query("select c from cart_item c where c.cart.id=:cartId and c.product.id = :productId")
    CartItem findByCartIdAndByProductId(Long cartId, String productId);

    @Query("select i from cart_item i where i.id = :cartItemId and i.cart.user.id = :userId ")
    CartItem findCartItemByUserIdAndCarItemId(Long userId, Long cartItemId);
}
