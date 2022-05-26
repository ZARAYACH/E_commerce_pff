package com.Ecommerce.Order;

import com.Ecommerce.Cart.Cart;
import com.Ecommerce.CartItem.CartItem;
import com.Ecommerce.CartItem.CartItemRepo;
import com.Ecommerce.CreditCard.CreditCard;
import com.Ecommerce.CreditCard.CreditCardService;
import com.Ecommerce.OrderItem.OrderItem;
import com.Ecommerce.OrderItem.OrderItemRepo;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepo orderRepo;

    private CreditCardService creditCardService;
    private UserRepo userRepo;

    private OrderItemRepo orderItemRepo;
    private CartItemRepo cartItemRepo;

    public ResponseEntity<?> makeOrderWholeCart(Authentication authentication, CreditCard card) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            Cart cart = user.getCart();
            if (cart != null) {
                if (creditCardService.valideCardInfo(card)) {
                    creditCardService.addCreditCard(authentication, card);
                    Order order = new Order();
                    Set<OrderItem> orderItems = new HashSet<>();
                    float totalPrice = 0;
                   if (cart.getCartItems().size() > 0 ){
                       for (CartItem cartItem : cart.getCartItems()) {
                           OrderItem orderItem = new OrderItem();
                           orderItem.setProduct(cartItem.getProduct());
                           orderItem.setQuantity(cartItem.getQuantity());
                           orderItem.setPriceByQuantity(cartItem.getProduct().getPrice() * cartItem.getQuantity());
                           totalPrice = totalPrice + orderItem.getPriceByQuantity();
                           orderItems.add(orderItem);
                       }
                   }else {
                       Map<String, String> error = new HashMap<>();
                       error.put("error", "you can't make a empty order");
                       return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(error);

                   }
                    order.setOrderItems(orderItems);
                    order.setTotalPrice(totalPrice);
                    order.setUser(user);
                    order.setTimeStamp(LocalDateTime.now());
                    Order save  = orderRepo.save(order);
                    for (OrderItem orderItem : order.getOrderItems()){
                        orderItem.setOrder(order);
                        orderItemRepo.save(orderItem);
                    }
                    cartItemRepo.deleteAll(cart.getCartItems());
                    Map<String, String> error = new HashMap<>();
                    error.put("success", "the purchase done successfully");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(error);

                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "your credit card info are invalide");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(error);

                }
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
