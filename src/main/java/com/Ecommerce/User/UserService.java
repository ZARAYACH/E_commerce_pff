package com.Ecommerce.User;

import com.Ecommerce.MailSender.JavaEmailSenderService;
import com.Ecommerce.Role.UserRoleAuthRepo;
import com.Ecommerce.UserCredentiels.UserCredentials;
import com.Ecommerce.UserCredentiels.UserCredentialsRepo;
import com.Ecommerce.UserCredentiels.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepo userRepo;
    private UserCredentialsService userCredService;
    private UserCredentialsRepo userCredRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private UserRoleAuthRepo userRoleAuthRepo;

    private JavaEmailSenderService mailSender;

    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        User user = userRepo.findUserByEmail(email);
        user.setUserCredentials(null);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> changeUserInfo(Authentication authentication, User newUser) {
        String email = authentication.getPrincipal().toString();
        User oldUser = userRepo.findUserByEmail(email);
        newUser.setId(oldUser.getId());
        if (newUser.getBirthDate() != null) {
            oldUser.setBirthDate(newUser.getBirthDate());
        }
        if (newUser.getFirstName() != null) {
            oldUser.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            oldUser.setLastName(newUser.getLastName());
        }
        if (newUser.getGender() != null) {
            oldUser.setGender(newUser.getGender());
        }
        if (newUser.getPhoneNumber() != null) {
            oldUser.setPhoneNumber(newUser.getPhoneNumber());
        }
        if (newUser.getImg() != null) {
            oldUser.setImg(newUser.getImg());
        }
        User user = userRepo.save(oldUser);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "something went wrong please repeat later");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        User user = userRepo.findUserByEmail(email);
        if (user != null){
            userRepo.delete(user);
            if (!userRepo.existsById(user.getId())) {
                return ResponseEntity.ok().body(user);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "something went wrong please repeat later");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> getUsers(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.findUserByEmail(email);
        if (admin != null){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userRepo.getAllUsers());
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> suspendUser(Authentication authentication, User userTemp) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.findUserByEmail(email);
        User user = userRepo.getById(userTemp.getId());
        if (user.isActive()) {
            userRepo.suspendUser(user.getId());
            Map<String, String> succes = new HashMap<>();
            succes.put("success", "the user :" + user.getEmail() + "is suspended with success");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(succes);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "user" + user.getEmail() + "already suspended");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    public ResponseEntity<?> unSuspendUser(Authentication authentication, User userTemp) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.findUserByEmail(email);
        User user = userRepo.getById(userTemp.getId());
        if (user.isActive() == true) {
            userRepo.unSuspendUser(user.getId());
            Map<String, String> succes = new HashMap<>();
            succes.put("success", "the user :" + user.getEmail() + "is activated with success");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(succes);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "user" + user.getEmail() + "already active");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }


    }

    public ResponseEntity<?> deleteUser(Authentication authentication, User toBeDel) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.findUserByEmail(email);
        if (userRepo.existsById(toBeDel.getId())) {
            User toBeDelete = userRepo.getById(toBeDel.getId());
            userRepo.delete(toBeDelete);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "user" + toBeDel.getId()+ "doesn't exist");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
        if (userRepo.existsById(toBeDel.getId())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "something went wrong");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        } else {
            Map<String, String> success = new HashMap<>();
            success.put("success", "the user with id ="+ toBeDel.getId() +" was deleted with success");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(success);
        }
    }

    //TODO:to be compeleted later on
    public ResponseEntity<?> UserSignUp(User user) {
        System.out.printf(user.getEmail());
        if (userRepo.existsByEmail(user.getEmail())==null) {
            if (user.getBirthDate().getYear() < LocalDate.now().getYear() || user.getBirthDate() != null) {
                if (userRepo.existsByPhoneNumber(user.getPhoneNumber())==null) {
                    if (user.getUserCredentials() != null) {
                        if (userCredService.cheekStrongestOfPassword(user.getUserCredentials())) {
                            String encodedPass =  passwordEncoder.encode(user.getUserCredentials().getPassword());
                            user.getUserCredentials().setEmail(user.getEmail());
                            user.getUserCredentials().setPassword(encodedPass);
                            user.setActive(false);
                            user.getUserCredentials().setActive(false);
                            user.setRoles(userRoleAuthRepo.getUserRoleAuthByName(String.valueOf(UserRoles.CUSTOMER)));
                            UserCredentials userCredentials = userCredRepo.save(user.getUserCredentials());
                            user.setUserCredentials(userCredentials);
                            user.getUserCredentials().setVerficationToken(UUID.randomUUID().toString());
                            userRepo.save(user);
                            String link = "http://localhost:8081/api/v1/verifyAccount?token="+user.getUserCredentials().getVerficationToken()+"&email="+user.getEmail();

                            mailSender.SendHtmlEmail(user.getEmail(),
                                    "medrassachanuwu@gmail.com",
                                    "verified your account",
                                    "<h1>verified your cprofile management profile</h1" +
                                            "<p>Please click <a href="+link+">HERE</a>to verified " + " your account </p>");

                            Map<String, String> success = new HashMap<>();
                            success.put("success", "the user with Email  :" +user.getEmail()+ " is successfully signed up");
                            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(success);

                        }else {
                            Map<String, String> error = new HashMap<>();
                            error.put("error", "the provided password should contain a number and lower and uper case letter and a charachter and tength betewenn 8 and 50");
                            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                        }
                    }else {
                        Map<String, String> error = new HashMap<>();
                        error.put("error", "please add a credientiel");
                        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                    }

                }else {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "this phone number is already exists");
                    return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                }
            }else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "invalid birth date");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }

        }else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "this email is already exists");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }
}
