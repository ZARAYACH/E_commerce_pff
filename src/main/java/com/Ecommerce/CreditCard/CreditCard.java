package com.Ecommerce.CreditCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "credit_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String cardHolderName;

    private Integer cartNumber;

    private Integer cvv;

    private LocalDate expirationDate;

    private String type;


}
