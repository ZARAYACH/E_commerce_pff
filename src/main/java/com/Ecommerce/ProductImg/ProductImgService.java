package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductImgService {

    private ProductImgRepo productImgRepo;
    private ProductRepo productRepo;

    public void addImgsToProduct(List<ProductImg> productImgs) {
        for (ProductImg productImg : productImgs) {
            if (productRepo.existsById(productImg.getProduct().getId())){
                productImgRepo.save(productImg);
            }
        }

    }
}
