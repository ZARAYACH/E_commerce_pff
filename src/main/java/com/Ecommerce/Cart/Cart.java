package com.Ecommerce.Cart;


import com.Ecommerce.CartItem.CartItem;
import com.Ecommerce.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.*;

@Entity(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    private User user;





}
