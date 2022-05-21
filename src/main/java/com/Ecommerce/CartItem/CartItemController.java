package com.Ecommerce.CartItem;

import com.Ecommerce.Cart.Cart;
import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cartItem")
@AllArgsConstructor
public class CartItemController {

    private CartItemService cartItemService;

    public List<CartItem> getCartsItemsByCart(Authentication authentication, Cart cart){
        List<CartItem> cartItems =cartItemService.getCartsItemsByCart(authentication,cart);
        if (cartItems != null){
            return cartItems;
        }else {
            return null;
        }
    }

    @PostMapping(path = "/addToCart")
    public ResponseEntity<?> addToCart(Authentication authentication, Product product){
        return cartItemService.addToCart(authentication,product);
    }
}
