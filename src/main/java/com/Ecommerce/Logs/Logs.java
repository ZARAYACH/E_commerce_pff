package com.Ecommerce.Logs;

import com.Ecommerce.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private String refreshToken;

    private String userAgent;

    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User user;

}
