package com.Ecommerce.CreditCard;

import com.Ecommerce.Cart.Cart;
import com.Ecommerce.Role.UserRoleAuthRepo;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CreditCardService {

    private CreditCardRepo creditCardRepo;

    private UserRepo userRepo;

    public ResponseEntity<?> addCreditCard(Authentication authentication, CreditCard card) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            if (this.valideCardInfo(card)) {
                if (creditCardRepo.existsByCardNumber(card.getCartNumber()) != null) {
                    creditCardRepo.save(card);
                    Map<String, String> success = new HashMap<>();
                    success.put("success", "card Added Successfully");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(success);
                }
            }

        } else {
            Map<String, String> success = new HashMap<>();
            success.put("error", "user doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
        }
        return null;
    }

    public boolean valideCardInfo(CreditCard card) {
        if (card.getCardHolderName() != null)
            if (card.getCartNumber() != null && String.valueOf(card.getCartNumber()).length() == 16)
                if (card.getCvv() != null && String.valueOf(card.getCvv()).length() == 3)
                    if (card.getExpirationDate() != null && !card.getExpirationDate().isBefore(LocalDate.now()))
                        return true;
                    else return false;
                else return false;
            else return false;
        else return false;
    }

    public ResponseEntity<?> getAllCards(Authentication authentication) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            List<CreditCard> card = creditCardRepo.findAll();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(card);
        } else {
            Map<String, String> success = new HashMap<>();
            success.put("error", "user doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
        }
    }


    public ResponseEntity<?> deleteCreditCard(Authentication authentication, CreditCard card1) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            CreditCard card = creditCardRepo.findCrediCardById(card1.getId());
            if (card != null) {
                creditCardRepo.deleteById(card.getId());
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("delete with success");
            } else {
                Map<String, String> success = new HashMap<>();
                success.put("error", "this card does not exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
            }
        } else {
            Map<String, String> success = new HashMap<>();
            success.put("error", "user doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
        }
    }
}
