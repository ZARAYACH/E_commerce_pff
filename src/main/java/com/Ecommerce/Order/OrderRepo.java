package com.Ecommerce.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("select o from orders o where o.user.id = :userId")
    List<Order> findOrdersByUserID(Long userId);

    @Query("select o from orders o where o.status = :stutus")
    List<Order> findAllOrdersByStatus(String stutus);

    @Query("select o from orders o where o.status = :status and o.user.id = :userId")
    List<Order> findOrdersByUserIdAndStatus(Long userId, String status);

    @Query("select o from orders o where o.id = :id ")
    Order findOrdersByID(int id);

    @Query("delete from orders o where o.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
