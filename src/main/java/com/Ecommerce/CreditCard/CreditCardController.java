package com.Ecommerce.CreditCard;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class CreditCardController {

    private CreditCardService creditCardService;
    @PostMapping(path = "/addCard")
    private ResponseEntity<?> addCreditCard(Authentication authentication,CreditCard card){
        return creditCardService.addCreditCard(authentication,card);
    }

    @GetMapping(path = "/admin/card/all")
    private ResponseEntity<?> getAllCards(Authentication authentication){
        return creditCardService.getAllCards(authentication);
    }
    @DeleteMapping(path = "/admin/card/delete")
    private ResponseEntity<?> deleteCreditCard(Authentication authentication,CreditCard card){
        return creditCardService.deleteCreditCard(authentication,card);
    }



}
