package com.Ecommerce.Order;

import com.Ecommerce.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {

}
