package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductImgController {

    private ProductImgService productImgService;
    @PostMapping(path = "/admin/addImgsToProduct")
    public void addImgsToProduct(@RequestBody List<ProductImg> productImgs){
        productImgService.addImgsToProduct(productImgs);
    }
}
