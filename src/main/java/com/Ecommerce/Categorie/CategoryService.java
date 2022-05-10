package com.Ecommerce.Categorie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategorieRepo categorieRepo;

    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(categorieRepo.getAllCategory());
    }

    public ResponseEntity<?> addCategory(Category category) {
        if (!categorieRepo.existsByName(category.getName())){
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(categorieRepo.save(category));
        }else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "category with name"+category.getName()+"already exists");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);

        }

    }

    public ResponseEntity<?> deleteCategorie(String categorieName) {
        if (categorieRepo.existsByName(categorieName)){
            categorieRepo.delete(categorieRepo.getCategorieByName(categorieName));
            if (!categorieRepo.existsByName(categorieName)){
                Map<String, String> succes = new HashMap<>();
                succes.put("success", "the categorie With name"+categorieName+"is deleted with succes");
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(succes);
            }else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "the category with name "+categorieName+" is not deleted");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateCatgory(Category newCategory) {
        if (categorieRepo.existsById(newCategory.getId())){
            Category category = categorieRepo.getById(newCategory.getId());
            category.setName(newCategory.getName());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(categorieRepo.save(category));

         }else {
            return  ResponseEntity.notFound().build();
        }
    }
}
