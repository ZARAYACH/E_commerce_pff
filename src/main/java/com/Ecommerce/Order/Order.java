package com.Ecommerce.Order;

import com.Ecommerce.OrderItem.OrderItem;
import com.Ecommerce.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    private Float totalPrice;

    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    private LocalDateTime timeStamp;

}
