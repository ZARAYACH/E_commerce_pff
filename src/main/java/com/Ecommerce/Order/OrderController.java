package com.Ecommerce.Order;

import com.Ecommerce.CreditCard.CreditCard;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class OrderController {

    private OrderService orderService;

    @PostMapping(path = "/makeOrder")
    private ResponseEntity<?> makeOrderWholeCart(Authentication authentication, CreditCard card){
        return orderService.makeOrderWholeCart(authentication,card);
    }
}
