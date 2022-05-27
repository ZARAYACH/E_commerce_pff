package com.Ecommerce.Logs;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class LogsController {

    private LogsService logsService;
    @PostMapping(path = "/logs/add")
    public ResponseEntity<?> addLog(Authentication authentication,@RequestBody Logs log){
        return logsService.addLog(authentication,log);

    }
}
