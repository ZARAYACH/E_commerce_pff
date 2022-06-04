package com.Ecommerce.Category;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/admin/category")
public class CategoryController {

    private CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllCategory(){
        return categoryService.getAllCategory();
    }
    @PostMapping(path = "/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @DeleteMapping(path = "/delete/{categorieName}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categorieName){
        return categoryService.deleteCategorie(categorieName);
    }
    @PutMapping(path = "/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        return categoryService.updateCatgory(category);
    }
}
