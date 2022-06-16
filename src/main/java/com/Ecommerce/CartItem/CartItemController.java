package com.Ecommerce.CartItem;

import com.Ecommerce.Cart.Cart;
import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class CartItemController {

    private CartItemService cartItemService;

    @PostMapping(path = "/user/cart/item/add")
    public ResponseEntity<?> addToCart(Authentication authentication,@RequestBody CartItem cartItem){
        return cartItemService.addToCart(authentication,cartItem);
    }

    @PutMapping(path = "/user/cart/item/add/quantity")
    public int addQuantity(Authentication authentication,@RequestBody CartItem cartItem){
        return cartItemService.addQuantity(authentication,cartItem);
    }
    @PutMapping(path = "/user/cart/item/minus/quantity")
    public int minusQuantity(Authentication authentication,@RequestBody CartItem cartItem){
        return cartItemService.minusQuantity(authentication,cartItem);
    }
    @DeleteMapping(path = "/user/cart/item/delete")
    public ResponseEntity<?> deleteItemFromCart(Authentication authentication,@RequestParam(name = "id") Long cartItem){
        return cartItemService.deleteItemFromCart(authentication,cartItem);
    }

    @DeleteMapping(path = "/user/cart/items/all/delete")
    public ResponseEntity<?> deleteAllItemsFromCart(Authentication authentication,@RequestBody List<CartItem> cartItems){
        return cartItemService.deleteFromCart(authentication,cartItems);
    }

}
