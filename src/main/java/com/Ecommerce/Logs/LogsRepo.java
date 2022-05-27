package com.Ecommerce.Logs;

import com.Ecommerce.User.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepo extends JpaRepository<Logs,Long> {

    boolean existsByRefreshToken(String refresh_token);

    boolean existsByRefreshTokenAndAndLogoutTimeIsNull(String refresh_token);

    @Query("select l from Logs l where l.user = :user and l.refreshToken is not null ")
    List<Logs> findAllByUserWhereReferechTokenIsNotNull(User user);
}
