package com.Ecommerce.Logs;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class LogsController {

    private LogsService logsService;
    @PostMapping(path = "/logs/add")
    public ResponseEntity<?> addLog(Authentication authentication,@RequestBody Logs log){
        return logsService.addLog(authentication,log);

    }
    @GetMapping(path = "/admin/logs/all")
    public ResponseEntity<?> getAllLogs(Authentication authentication){
        return logsService.getAllLog(authentication);
    }

    @GetMapping(path = "/admin/logs/{userId}/all")
    public ResponseEntity<?> getAllLogsByUser(Authentication authentication,@PathParam("userId") Long userID){
        return logsService.getAllLogsByUser(authentication,userID);

    }
    @DeleteMapping(path = "/admin/logs/deleteAll")
    public ResponseEntity<?> deleteAllLogs(Authentication authentication){
        return logsService.deleteAllLogs(authentication);

    }
    @DeleteMapping(path = "/admin/logs/{userId}/deleteAll")
    public ResponseEntity<?> deleteAllLogsByuserId(Authentication authentication,@PathParam("userId")Long userId ){
        return logsService.deleteAllLogsByuserId(authentication,userId);

    }

}
