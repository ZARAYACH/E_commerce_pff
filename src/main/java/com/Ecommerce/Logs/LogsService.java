package com.Ecommerce.Logs;

import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogsService {

    private LogsRepo logsRepo;
    private UserRepo userRepo;

    public ResponseEntity<?> addLog(Authentication authentication, Logs logs) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            logs.setUser(user);
            if (logs.getRefreshToken() != null) {
                Logs saved = logsRepo.save(logs);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
            } else {
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("please add the refrechToken");
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
