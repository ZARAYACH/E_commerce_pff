package com.Ecommerce.User;

import com.Ecommerce.Cart.Cart;
import com.Ecommerce.Logs.Logs;
import com.Ecommerce.Order.Order;
import com.Ecommerce.Role.UserRoleAuth;
import com.Ecommerce.UserCredentiels.UserCredentials;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.util.List;

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
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(name = "UNIQUE_USER_ROLE",columnNames ={"user_id","role_id"} ) )
    private Collection<UserRoleAuth> roles = new ArrayList<>();

    private LocalDate birthDate;
    private boolean isActive;
    private boolean isLogedIn;
    private String phoneNumber;
    private String img;


    @OneToOne(cascade = CascadeType.REMOVE,fetch = EAGER)
    @JoinColumn(name = "userCrendials_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserCredentials userCredentials;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL,fetch = LAZY,mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user",fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Logs> logs;
}
