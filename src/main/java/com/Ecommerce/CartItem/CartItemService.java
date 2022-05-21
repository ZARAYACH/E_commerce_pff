package com.Ecommerce.CartItem;

import com.Ecommerce.Cart.Cart;
import com.Ecommerce.Cart.CartRepo;
import com.Ecommerce.Product.Product;
import com.Ecommerce.Product.ProductRepo;
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
public class CartItemService {

    private CartItemRepo cartItemRepo;

    private UserRepo userRepo;
    private CartRepo cartRepo;

    private ProductRepo productRepo;

    public ResponseEntity<?> addToCart(Authentication authentication, Product product) {
        if (userRepo.existsByEmail(authentication.getPrincipal().toString()) != null) {
            User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
            if (productRepo.existsById(product.getId())) {
                if (user.getCart() != null) {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(user.getCart());
                    cartItem.setProduct(product);
                    cartItemRepo.save(cartItem);
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "you can't add to cart unless you activate your account");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "this product does not exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "user does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
        }
        return null;
    }

    public ResponseEntity<?> deleteFromCart(Authentication authentication, List<CartItem> cartItems) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            Cart cart = user.getCart();
            if (cart != null) {
                for (CartItem cartItem : cartItems) {
                    if (cartRepo.existsById(cartItem.getId())) {
                        cartItemRepo.delete(cartItem);
                    }
                }
                Map<String, String> error = new HashMap<>();
                error.put("success", "the items were deleted successfully");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(error);

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

    public List<CartItem> getCartItemsByCart(Authentication authentication, Cart cart) {
        if (cartRepo.existsById(cart.getId())) {
            return cartItemRepo.findAllByCartId(cart.getId());
        } else {
            return null;
        }
    }

}
