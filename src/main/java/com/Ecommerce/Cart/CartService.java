package com.Ecommerce.Cart;

import com.Ecommerce.CartItem.CartItem;
import com.Ecommerce.CartItem.CartItemService;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepo cartRepo;
    private UserRepo userRepo;

    private CartItemService cartItemService;

    public void addCart(User user) {
        if (userRepo.existsById(user.getId())) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }
    }

//    public ResponseEntity<?> getAllCartItem(Authentication authentication) {
//        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
//        if (user.getCart() != null) {
//           List<CartItem> cartItems =  cartItemService.getCartsItemsByCart(authentication,user.getCart());
//
//        }
//    }
}
