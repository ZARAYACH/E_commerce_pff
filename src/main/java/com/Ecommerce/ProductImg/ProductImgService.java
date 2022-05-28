package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.Product;
import com.Ecommerce.Product.ProductRepo;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductImgService {

    private ProductImgRepo productImgRepo;
    private ProductRepo productRepo;
    private UserRepo userRepo;


    public ResponseEntity<?> addImgsToProduct(Authentication authentication, List<ProductImg> productImgs, String productId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            Product product = productRepo.findProductById(productId);
            if (product != null) {
                for (ProductImg productImg : productImgs) {
                    productImg.setProduct(product);
                    productImgRepo.save(productImg);
                }
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("added successfully");
            }else{
                Map<String, String> error = new HashMap<>();
                error.put("error", "product with id"+productId+"does not exists");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    public ResponseEntity<?> updateProductImg(Authentication authentication,String productId, ProductImg img, Long imgId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null){
            Product product = productRepo.findProductById(productId);
            if (product!=null){
                ProductImg productImg = productImgRepo.findImgById(imgId);
                if (productImg!=null){
                    productImg.setProduct(product);
                    productImg.setPath(img.getPath());
                    productImg.setPrimaryImg(img.isPrimaryImg());
                    ProductImg saved =  productImgRepo.save(productImg);
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
                }else {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "the image with id"+imgId+"does not exists");
                    return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                }
            }else{
                Map<String, String> error = new HashMap<>();
                error.put("error", "product with id"+productId+"does not exists");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteProductImg(Authentication authentication,Long imgId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin!= null){
            ProductImg productImg = productImgRepo.findImgById(imgId);
            if (productImg!=null){
                productImgRepo.delete(productImg);
                return  ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("the image with id"+imgId+"deleted with success");

            }else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "the image with id"+imgId+"does not exists");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);

            }
        }else {
            return ResponseEntity.notFound().build();

        }
    }
}
