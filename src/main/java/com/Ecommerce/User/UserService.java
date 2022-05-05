package com.Ecommerce.User;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepo userRepo;

    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        User user = userRepo.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> changeUserInfo(Authentication authentication, User newUser) {
        String email = authentication.getPrincipal().toString();
        User oldUser = userRepo.getUserByEmail(email);
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
        User user = userRepo.getUserByEmail(email);
        userRepo.delete(user);
        if (!userRepo.existsById(user.getId())) {
            return ResponseEntity.ok().body(user);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "something went wrong please repeat later");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    public ResponseEntity<?> getUsers(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.getUserByEmail(email);
        if (admin.getRoles().contains("admin")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userRepo.getAllUsers());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> suspendUser(Authentication authentication, User userTemp) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.getUserByEmail(email);
        User user = userRepo.getById(userTemp.getId());
        if (user.isActive() == true) {
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
        User admin = userRepo.getUserByEmail(email);
        User user  = userRepo.getById(userTemp.getId());
        if (user.isActive()==true){
            userRepo.unSuspendUser(user.getId());
            Map<String, String> succes = new HashMap<>();
            succes.put("success", "the user :"+user.getEmail()+"is activated with success");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(succes);
        }else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "user"+user.getEmail()+"already active");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }


    }

    public ResponseEntity<?> deleteUser(Authentication authentication, User toBeDel) {
        String email = authentication.getPrincipal().toString();
        User admin = userRepo.getUserByEmail(email);
        if (userRepo.existsById(toBeDel.getId())){
            userRepo.delete(toBeDel);
        }else{
            Map<String, String> error = new HashMap<>();
            error.put("error", "user"+toBeDel.getEmail()+"doesn't exist");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
        if (userRepo.existsById(toBeDel.getId())){
            Map<String, String> error = new HashMap<>();
            error.put("error", "something went wrong");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }else{
            Map<String, String> success = new HashMap<>();
            success.put("success", "the user :" + toBeDel.getEmail() + "was deleted with success");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(success);
        }
    }
    //TODO:to be compeleted later on
//    public ResponseEntity<?> UserSignUp(User user) {
//        if (!userRepo.existsByEmail(user.getEmail())){
//            /
//        }
//    }
}
