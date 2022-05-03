package com.Ecommerce.UserCredientiels;

import com.Ecommerce.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "userCredentials")
    private User user;

    @Column(unique = true)
    private String email;
    private String password;
    private boolean isValid;
    private LocalDateTime dateTime;



}
