package com.Ecommerce.User;

import com.Ecommerce.Role.UserRoleAuth;
import com.Ecommerce.UserCredentiels.UserCredentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.*;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String gender;

    @ManyToMany(fetch = EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<UserRoleAuth> roles = new ArrayList<>();

    private LocalDate birthDate;
    private boolean isActive;
    private String phoneNumber;
    private String img;


    @OneToOne(cascade = CascadeType.ALL,fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userCrendials_id", referencedColumnName = "id")
    private UserCredentials userCredentials;


}
