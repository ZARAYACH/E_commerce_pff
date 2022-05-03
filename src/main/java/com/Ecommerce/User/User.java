package com.Ecommerce.User;

import com.Ecommerce.Role.Role;
import com.Ecommerce.UserCredientiels.UserCredentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

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
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    private String gender;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private LocalDate birthDate ;
    private boolean isActive;
    private String phoneNumber;
    private String img;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userCrendials_id", referencedColumnName = "id")
    private UserCredentials userCredentials ;



}
