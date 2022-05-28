package com.Ecommerce.Order;

import com.Ecommerce.CreditCard.CreditCard;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class OrderController {

    private OrderService orderService;

    @PostMapping(path = "/user/makeOrder")
    private ResponseEntity<?> makeOrderWholeCart(Authentication authentication,@RequestBody CreditCard card){
        return orderService.makeOrderWholeCart(authentication,card);
    }
    @GetMapping(path = "/admin/order/all")
    public ResponseEntity<?> getAllOrders(Authentication authentication){
        return orderService.getAllOrders(authentication);
    }

    @GetMapping(path = "/admin/order/all/user/{userId}")
    public ResponseEntity<?> getAllOrdersByUser(Authentication authentication, @PathParam("userId")Long userId){
        return orderService.getAllOrdersByUser(authentication,userId);
    }

    @GetMapping(path = "/admin/orders/status")
    public ResponseEntity<?> getAllOrdersBystatus(Authentication authentication,@RequestParam(name = "status")String stutus){
        return orderService.getAllOrdersBystatus(authentication,stutus);
    }
    @GetMapping(path = "/admin/order/status/all/user/{userId}")
    public ResponseEntity<?> getAllOrdersByYserAndstatus(Authentication authentication,@RequestParam(name = "status")String stutus,@PathParam("userId")Long userId){
        return orderService.getAllOrdersByUserAndstatus(authentication,stutus,userId);
    }
    @PutMapping(path = "/admin/order/changestatus")
    public ResponseEntity<?> changeOrderStatus(Authentication authentication,@RequestParam(name = "status")OrderStatus newStutus,@RequestBody Order order){
        return orderService.changeOrderStatus(authentication,order,newStutus);
    }

    @DeleteMapping(path = "/admin/delete/all/orders")
    public ResponseEntity<?> deleteAllOrders(Authentication authentication){
        return orderService.deleteAllOrders(authentication);
    }
    @DeleteMapping(path = "/admin/order/delete/all/user/{userId}")
    public ResponseEntity<?> deleteAllOrders(Authentication authentication,@PathParam("userId")Long userId){
        return orderService.deleteAllUserOrders(authentication,userId);
    }


}
