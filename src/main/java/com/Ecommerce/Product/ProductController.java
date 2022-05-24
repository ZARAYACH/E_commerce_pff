package com.Ecommerce.Product;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    //public
    @GetMapping(path = "/products/categorie/{categorie}")
    public ResponseEntity<?> getProductsByCat(@PathVariable String categorieName) {
        return productService.getProductBycategorie(categorieName);

    }
    @GetMapping(path = "/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }
    //admin
    @PostMapping(path = "/admin/product/add")
    public ResponseEntity<?> addProduct(Authentication authentication, @RequestBody Product product){
        return productService.addProduct(authentication,product);
    }
    @DeleteMapping(path = "/admin/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(Authentication authentication,@PathVariable String id){
        return productService.deleteProduct(authentication,id);
    }
    @PutMapping(path = "/admin/product/update")
    public ResponseEntity<?> updateProduct(Authentication authentication,@RequestBody Product newProduct){
        return productService.updateProduct(authentication,newProduct);
    }
}
