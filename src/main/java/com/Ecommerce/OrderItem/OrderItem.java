package com.Ecommerce.OrderItem;

import com.Ecommerce.Order.Order;
import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.geom.FlatteningPathIterator;

@Entity(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer quantity;

    private Float priceByQuantity;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

}
