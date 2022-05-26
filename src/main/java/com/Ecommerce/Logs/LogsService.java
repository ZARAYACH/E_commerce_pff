package com.Ecommerce.Logs;

import com.Ecommerce.confige.ApplicationContextHolder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.Ecommerce.User.User;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogsService {

    private LogsRepo logsRepo;
    public void addLog(Logs logs){
            logsRepo.save(logs);
   }
}
