package com.Ecommerce.Product;

import com.Ecommerce.Category.Category;
import com.Ecommerce.Category.CategoryRepo;
import com.Ecommerce.User.UserRepo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor

public class ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categorieRepo;

    private UserRepo userRepo;


    public ResponseEntity<?> getProductBycategorie(String categorieName) {
        if (categorieRepo.existsByName(categorieName)) {
            Category category = categorieRepo.getCategorieByName(categorieName);
            List<Product> products = productRepo.getProductsByCategory(category);
            return ResponseEntity.ok().body(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getProductById(String id) {
        if (productRepo.existsById(id)) {
            return ResponseEntity.ok().body(productRepo.getById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> addProduct(Authentication authentication, Product product) {
        if (product.getCategory().getName() !=null ){
            Category category = categorieRepo.getCategorieByName(product.getCategory().getName());
            product.setCategory(category);
        }else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "set a valid category name");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
        Product success = productRepo.save(product);
        if (productRepo.existsById(success.getId())){
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(success);
        }else{
            Map<String, String> error = new HashMap<>();
            error.put("error", "this product is not added please repeat later");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }

    public ResponseEntity<?> deleteProduct(Authentication authentication, String id) {
        if (productRepo.existsById(id)){
            productRepo.deleteById(id);
            if(!productRepo.existsById(id)){
                Map<String, String> succes = new HashMap<>();
                succes.put("success", "the product With id"+id+"is deleted with succes");
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(succes);
            }else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "this product is not deleted please repeat later");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateProduct(Authentication authentication, Product newProduct) {
        if (productRepo.existsById(newProduct.getId())){
            Product product = productRepo.getById(newProduct.getId());
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setPrice(newProduct.getPrice());
            product.setCategory(newProduct.getCategory());

            Product success = productRepo.save(product);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(success);

        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll());
    }
}
