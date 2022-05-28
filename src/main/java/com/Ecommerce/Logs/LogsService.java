package com.Ecommerce.Logs;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import com.google.gson.annotations.JsonAdapter;
import lombok.AllArgsConstructor;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ResponseEntity<?> getAllLog(Authentication authentication) {
        User user = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (user != null) {
            List<Logs> logs = logsRepo.findAll();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(logs);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> getAllLogsByUser(Authentication authentication, Long userID) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            User user = userRepo.findUserById(userID);
            if (user != null) {
                List<Logs> logs = logsRepo.findAllByUserId(userID);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(logs);
            } else {
                Map<String, String> success = new HashMap<>();
                success.put("error", "user doesn't exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteAllLogs(Authentication authentication) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            logsRepo.deleteAll();
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteAllLogsByuserId(Authentication authentication, Long userId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            User user = userRepo.findUserById(userId);
            if (user!= null){
                logsRepo.deleteAllByUserId(userId);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("All logs related to this user was deleted successfully");
            }else {
                Map<String, String> success = new HashMap<>();
                success.put("error", "user doesn't exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(success);
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
