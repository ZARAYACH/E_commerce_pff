package com.Ecommerce.Order;

import com.Ecommerce.OrderItem.OrderItem;
import com.Ecommerce.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private Float totalPrice;

    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime timeStamp;

}
