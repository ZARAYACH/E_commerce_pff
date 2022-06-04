package com.Ecommerce.ProductImg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepo extends JpaRepository<ProductImg,Long> {
    @Query("select i from ProductImg i where i.id = :imgId")
    ProductImg findImgById(Long imgId);

}
