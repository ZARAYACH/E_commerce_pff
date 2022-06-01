package com.Ecommerce.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard,Long> {

    @Query("select c from credit_card c where c.cartNumber = :cartNumber ")
    CreditCard existsByCardNumber(@Param(value = "cartNumber") String cartNumber);

    @Query("select c from credit_card c where c.id = :id")
    CreditCard findCrediCardById(Long id);

}
