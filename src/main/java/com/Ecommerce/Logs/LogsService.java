package com.Ecommerce.Logs;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;

import com.Ecommerce.User.User;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
public class LogsService {

    private LogsRepo logsRepo;
    public void addLog(Logs logs){
        logsRepo.save(logs);

    }
}
