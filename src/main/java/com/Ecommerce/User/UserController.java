package com.Ecommerce.User;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    private UserService userService;


    @PostMapping(path = "/user/signup")
    private ResponseEntity<?> UserSignUp(@RequestBody User user){
        return userService.UserSignUp(user);
    }

    @PostMapping(path = "/user/logout")
    public ResponseEntity<?> userLougout(Authentication authentication){
        return userService.logout(authentication);

    }
    //user methods
    @GetMapping(path = "/user/info")
    public ResponseEntity<?> getUserInfo(Authentication authentication){
        return  userService.getUserInfo(authentication);
    }
    @PutMapping(path = "/user/changeInfo")
    public ResponseEntity<?> changeUserInfo(Authentication authentication,@RequestBody User newUser){
        return userService.changeUserInfo(authentication,newUser);
    }
    @DeleteMapping(path = "/user/deleteAccount")
    public ResponseEntity<?> deleteAccount(Authentication authentication){
        return userService.deleteAccount(authentication);
    }

    //Admin methods

    @GetMapping(path = "/admin/getUsers")
    public ResponseEntity<?> getUsers(Authentication authentication){
        return userService.getUsers(authentication);
    }
    @PostMapping(path = "/admin/suspendUser")
    public ResponseEntity<?> suspendUser(Authentication authentication,@RequestBody User user){
        return userService.suspendUser(authentication,user);
    }
    @PostMapping(path = "/admin/unSuspendUser")
    public ResponseEntity<?> unSuspendUser(Authentication authentication,@RequestBody User user){
        return userService.unSuspendUser(authentication,user);
    }
    @DeleteMapping(path = "/admin/deleteUser")
    public ResponseEntity<?> deleteUser(Authentication authentication,@RequestBody User toBeDel){
        return userService.deleteUser(authentication,toBeDel);
    }
}
