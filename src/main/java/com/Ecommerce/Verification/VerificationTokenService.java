package com.Ecommerce.Verification;

import com.Ecommerce.Cart.CartController;
import com.Ecommerce.Cart.CartService;
import com.Ecommerce.MailSender.JavaEmailSenderService;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;

import com.Ecommerce.UserCredentiels.UserCredentials;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VerificationTokenService {

    private final UserRepo userRepo;
    private final JavaEmailSenderService emailSenderService;
    private final BCryptPasswordEncoder passwordEncoder;

    private CartController cartController;
    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public ResponseEntity<?> verifyAnAccount(String token, String email) {
        if (userRepo.existsByEmail(email)!=null) {
            User user = userRepo.findUserByEmail(email);
            if (!user.isActive()) {
                String verificationToken = user.getUserCredentials().getVerficationToken();
                if (Objects.equals(verificationToken, token)) {
                    user.getUserCredentials().setActive(true);
                    user.setActive(true);
                    cartController.AddCartUser(user);
                    userRepo.save(user);
                    return ResponseEntity.ok().body("this account has been successfully activated");
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("this token is invalid please check your inbox");
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("this User is already active");
            }

        } else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> ResendTheToken(String email) {
        if (userRepo.existsByEmail(email)!=null) {
            User user = userRepo.findUserByEmail(email);
            if (!user.isActive()) {
                user.getUserCredentials().setVerficationToken(generateVerificationToken());
                String link = "http://localhost:8080/api/v1/verifyAccount?token=" + user.getUserCredentials().getVerficationToken() + "email=" + user.getEmail();
                emailSenderService.SendHtmlEmail(user.getEmail(),
                        "mohamedachbani50@gmail.com",
                        "Please verified your account ",
                        "<h1>verified your cprofile management profile</h1" +
                                "<p>Please click <a href=" + link + ">HERE</a>to verified " + " your account </p>");
                return ResponseEntity.ok().body("the verification link has been resend");
            } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("this user is already verified");
        } else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> setPasswordForNewUser(String token, UserCredentials userCredentials) {
        if (userRepo.existsById(userCredentials.getUser().getId())){
            User user = userRepo.findUserByEmail(userCredentials.getEmail());
            if (user.getUserCredentials().getVerficationToken() == token){
                user.getUserCredentials().setPassword(passwordEncoder.encode(userCredentials.getPassword()));
                user.setActive(true);
                userRepo.save(user);
                return ResponseEntity.ok().body("your account now activated and you can login with your credentials");
            }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("the token is invalid");
        }else return ResponseEntity.notFound().build();
    }
}
