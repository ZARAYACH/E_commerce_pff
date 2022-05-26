package com.Ecommerce.CartItem;


import com.Ecommerce.Cart.Cart;
import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id",referencedColumnName = "id",nullable = false)
    private Cart cart;






}
