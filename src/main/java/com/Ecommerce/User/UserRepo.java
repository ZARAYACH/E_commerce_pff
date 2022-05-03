package com.Ecommerce.User;

import com.Ecommerce.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

}
