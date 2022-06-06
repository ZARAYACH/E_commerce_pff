package com.Ecommerce.Product;

import com.Ecommerce.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {

    List<Product> getProductsByCategory(Category category);

    @Query("select p from product p where p.id = :productId")
    Product findProductById(String productId);
}
