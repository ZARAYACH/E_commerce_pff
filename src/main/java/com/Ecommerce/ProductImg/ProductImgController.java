package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductImgController {

    private ProductImgService productImgService;

    @GetMapping(value = "/images/products/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] addImgsToProduct(@PathVariable String imageName) throws IOException {
        return productImgService.getImageByNameFromDesk(imageName);
    }

    @PostMapping(path = "/admin/image/add")
    public ResponseEntity<?> addImgsToProduct(Authentication authentication, @RequestParam("images") List<MultipartFile> productImgs, @PathParam("productId")String productId) throws IOException {
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
