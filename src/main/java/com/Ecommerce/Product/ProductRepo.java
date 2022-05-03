package com.Ecommerce.Product;

import com.Ecommerce.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {

}
