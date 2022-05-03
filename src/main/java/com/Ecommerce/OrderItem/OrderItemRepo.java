package com.Ecommerce.OrderItem;

import com.Ecommerce.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {

}
