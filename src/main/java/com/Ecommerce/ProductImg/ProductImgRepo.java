package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepo extends JpaRepository<ProductImg,Long> {

}
