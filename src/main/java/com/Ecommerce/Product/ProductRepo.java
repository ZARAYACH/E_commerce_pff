package com.Ecommerce.Product;

import com.Ecommerce.Categorie.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {

    List<Product> getProductsByCategorie(Category category);
}
