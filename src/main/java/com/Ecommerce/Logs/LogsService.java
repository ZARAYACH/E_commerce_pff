package com.Ecommerce.Logs;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogsService {

    private LogsRepo logsRepo;
    public void addLog(Logs logs){
        logsRepo.save(logs);

    }
}
