package com.Ecommerce.Cart;


import lombok.*;

import javax.persistence.*;

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






}
