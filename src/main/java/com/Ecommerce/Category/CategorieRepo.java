package com.Ecommerce.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepo extends JpaRepository<Category,Long> {

    Boolean existsByName(String name);

    Category getCategorieByName(String categorieName);

    @Query("select c from category c")
    List<Category> getAllCategory();
}
