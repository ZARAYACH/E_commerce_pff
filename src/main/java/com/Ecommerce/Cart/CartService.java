package com.Ecommerce.Cart;

import com.Ecommerce.CartItem.CartItem;
import com.Ecommerce.CartItem.CartItemRepo;
import com.Ecommerce.CartItem.CartItemService;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepo cartRepo;
    private UserRepo userRepo;
    private CartItemService cartItemService;

    private CartItemRepo cartItemRepo;

    public void addCart(User user) {
        if (userRepo.existsById(user.getId())) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }
    }

    public ResponseEntity<?> getAllCartItem(Authentication authentication) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user.getCart() != null) {
            List<CartItem> cartItems = cartItemService.getCartItemsByCart(authentication, user.getCart());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(cartItems);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "please go activate your account");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    public List<CartItem> returnCartItems(Authentication authentication, Cart cart) {
        return cartItemService.getCartItemsByCart(authentication, cart);
    }

    public ResponseEntity<?> deleteAllCartItems(Authentication authentication) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            Cart cart = cartRepo.findCartById(user.getCart().getId());
            if (cart != null) {
                List<CartItem> cartItems = cartItemRepo.findAllByCartId(cart.getId());
                cartItemRepo.deleteAll(cartItems);
                Map<String, String> error = new HashMap<>();
                error.put("success", "deleted wth success");
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(error);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "please go activate your account");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "user doesn't exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }
}
