package com.Ecommerce.Logs;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepo extends JpaRepository<Logs,Long> {

}
