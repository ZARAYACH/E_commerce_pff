package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductImgController {

    private ProductImgService productImgService;
    @PostMapping(path = "/admin/image/add")
    public ResponseEntity<?> addImgsToProduct(Authentication authentication, @RequestBody List<ProductImg> productImgs, @PathParam("productId")String productId){
       return productImgService.addImgsToProduct(authentication,productImgs,productId);
    }
    @PutMapping(path = "/admin/poduct/{productId}/update/img")
    public ResponseEntity<?> updateProductImg(Authentication authentication,@PathParam("productId")String productId,@RequestBody ProductImg img,@RequestParam(name = "imgId")Long imgId){
       return productImgService.updateProductImg(authentication,productId,img,imgId);
    }
    @DeleteMapping(path = "/admin/poduct/delete/img")
    public ResponseEntity<?> updateProduct(Authentication authentication,@PathParam("id")Long imgId){
       return  productImgService.deleteProductImg(authentication,imgId);
    }

}
