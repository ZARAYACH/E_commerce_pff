package com.Ecommerce.Cart;

import com.Ecommerce.Product.Product;
import com.Ecommerce.User.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class CartController {

    private CartService cartService;
    public void AddCartUser(User user){
         cartService.addCart(user);
    }

    @GetMapping(path = "/user/cart/Cartitems/all")
    private ResponseEntity<?> getAllCartItem(Authentication authentication){
        return cartService.getAllCartItem(authentication);
    }

    @DeleteMapping(path = "/user/cart/Cartitems/deleteAll")
    private ResponseEntity<?> deleteAllCartItems(Authentication authentication){
        return cartService.deleteAllCartItems(authentication);
    }



}
