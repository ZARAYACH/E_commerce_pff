package com.Ecommerce.CreditCard;

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
                creditCardRepo.save(card);
                Map<String, String> success = new HashMap<>();
                success.put("success", "card Added Successfully");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(success);
            } else {
                Map<String, String> success = new HashMap<>();
                success.put("error", "card info are invalid");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(success);
            }
        } else {
            Map<String, String> success = new HashMap<>();
            success.put("error", "user doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
        }
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
}
