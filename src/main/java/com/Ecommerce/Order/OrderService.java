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

import java.time.LocalDateTime;
import java.util.*;

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
                    if (cart.getCartItems().size() > 0) {
                        for (CartItem cartItem : cart.getCartItems()) {
                            OrderItem orderItem = new OrderItem();
                            orderItem.setProduct(cartItem.getProduct());
                            orderItem.setQuantity(cartItem.getQuantity());
                            orderItem.setPriceByQuantity(cartItem.getProduct().getPrice() * cartItem.getQuantity());
                            totalPrice = totalPrice + orderItem.getPriceByQuantity();
                            orderItems.add(orderItem);
                        }
                    } else {
                        Map<String, String> error = new HashMap<>();
                        error.put("error", "you can't make a empty order");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(error);

                    }
                    order.setOrderItems(orderItems);
                    order.setTotalPrice(totalPrice);
                    order.setUser(user);
                    order.setStatus(OrderStatus.ontheway);
                    order.setTimeStamp(LocalDateTime.now());
                    Order save = orderRepo.save(order);
                    for (OrderItem orderItem : order.getOrderItems()) {
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

    public ResponseEntity<?> getAllOrders(Authentication authentication) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            List<Order> orders = orderRepo.findAll();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getAllOrdersByUser(Authentication authentication, Long userID) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            User user = userRepo.findUserById(userID);
            if (user != null) {
                List<Order> orders = orderRepo.findOrdersByUserID(userID);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orders);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "user doesn't exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getAllOrdersBystatus(Authentication authentication, String stutus) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            List<Order> orders = orderRepo.findAllOrdersByStatus(stutus);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getAllOrdersByUserAndstatus(Authentication authentication, String stutus,Long userId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            User user = userRepo.findUserById(userId);
            if (user != null) {
                List<Order> orders = orderRepo.findOrdersByUserIdAndStatus(userId,stutus);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orders);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "user doesn't exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> changeOrderStatus(Authentication authentication, Order order1,OrderStatus status) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            Order order = orderRepo.findOrdersByID(order1.getId());
            if (order != null){
                order.setStatus(status);
                Order saved =orderRepo.save(order);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
            }else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "order doesn't exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteAllOrders(Authentication authentication) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            orderRepo.deleteAll();
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();

        }
    }

    public ResponseEntity<?> deleteAllUserOrders(Authentication authentication, Long userId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            User user = userRepo.findUserById(userId);
            if (user != null) {
                orderRepo.deleteAllByUserId(userId);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("deleted with success");
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "user doesn't exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getAllOrdersForUser(Authentication authentication) {
        User user = userRepo.existsByEmail(authentication.getPrincipal().toString());
        if (user != null){
            List<Order> orders = user.getOrders();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orders);
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}
