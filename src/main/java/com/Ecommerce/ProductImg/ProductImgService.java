package com.Ecommerce.ProductImg;

import com.Ecommerce.Product.Product;
import com.Ecommerce.Product.ProductRepo;
import com.Ecommerce.User.User;
import com.Ecommerce.User.UserRepo;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductImgService {

    private ProductImgRepo productImgRepo;
    private ProductRepo productRepo;
    private UserRepo userRepo;

    public ProductImgService(ProductImgRepo productImgRepo, ProductRepo productRepo, UserRepo userRepo) {
        this.productImgRepo = productImgRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    Path uplaodPath = Paths.get("./images/products");
    private final String PRODUCT_IMG_LOCATION = uplaodPath.toFile().getAbsolutePath();

    public ResponseEntity<?> addImgsToProduct(Authentication authentication, List<MultipartFile> files, String productId) throws IOException {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            Product product = productRepo.findProductById(productId);
            if (product != null) {
                List<ProductImg> productImgs = new ArrayList<>();
                if (files.size() > 0) {
                    for (MultipartFile file : files) {
                        if (Objects.equals(file.getContentType(), "image/jpg") || Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/svg+xml") || Objects.equals(file.getContentType(), "image/svg") || Objects.equals(file.getContentType(), "image/png")) {
                            if (Objects.equals(file.getContentType(), "image/jpg")) {
                                File img = new File(PRODUCT_IMG_LOCATION + UUID.randomUUID() + ".jpg");
                                productImgs.add(createImg(img, file, product));
                            } else if (Objects.equals(file.getContentType(), "image/jpeg")) {
                                File img = new File(PRODUCT_IMG_LOCATION + "/" + UUID.randomUUID() + ".jpeg");
                                productImgs.add(createImg(img, file, product));
                            } else if (Objects.equals(file.getContentType(), "image/svg") || Objects.equals(file.getContentType(), "image/svg+xml")) {
                                File img = new File(PRODUCT_IMG_LOCATION + "/" + UUID.randomUUID() + ".svg");
                                productImgs.add(createImg(img, file, product));
                            } else if (Objects.equals(file.getContentType(), "image/png")) {
                                File img = new File(PRODUCT_IMG_LOCATION + "/" + UUID.randomUUID() + ".png");
                                productImgs.add(createImg(img, file, product));
                            }
                        } else {
                            Map<String, String> error = new HashMap<>();
                            error.put("error", "this type of files is uncepptable please add this files types(jpg,svg,png,jpeg)");
                            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                        }
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "you can't add a null to a product");
                    return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                }
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(productImgs);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "product with id" + productId + "does not exists");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        } else {
            return ResponseEntity.notFound().build();
        }


    }


    public ResponseEntity<?> updateProductImg(Authentication authentication, String productId, ProductImg img, Long imgId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            Product product = productRepo.findProductById(productId);
            if (product != null) {
                ProductImg productImg = productImgRepo.findImgById(imgId);
                if (productImg != null) {
                    productImg.setProduct(product);
                    productImg.setPath(img.getPath());
                    productImg.setPrimaryImg(img.isPrimaryImg());
                    ProductImg saved = productImgRepo.save(productImg);
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saved);
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "the image with id" + imgId + "does not exists");
                    return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "product with id" + productId + "does not exists");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteProductImg(Authentication authentication, Long imgId) {
        User admin = userRepo.findUserByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            ProductImg productImg = productImgRepo.findImgById(imgId);
            if (productImg != null) {
                productImgRepo.delete(productImg);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("the image with id" + imgId + "deleted with success");

            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "the image with id" + imgId + "does not exists");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);

            }
        } else {
            return ResponseEntity.notFound().build();

        }
    }

    private ProductImg createImg(File file, MultipartFile img, Product product) throws IOException {
        if (file.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(img.getBytes());
            outputStream.close();
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);
            productImg.setPrimaryImg(true);
            String absolutePath = file.getAbsolutePath();
            productImg.setPath(absolutePath.substring(absolutePath.indexOf("\\images")).replace("\\", "/"));
            return productImgRepo.save(productImg);
        }
        return null;
    }

    public byte[] getImageByNameFromDesk(String imageName) throws IOException {
        File dir = new File(PRODUCT_IMG_LOCATION);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().equals(imageName)) {
                InputStream in = getClass()
                        .getResourceAsStream(file.getName());
                return IOUtils.toByteArray(in);
            }
        }
        return null;
    }

    public ResponseEntity<?> setImagePrimary(Authentication authentication, ProductImg productImg) {
        User admin = userRepo.existsByEmail(authentication.getPrincipal().toString());
        if (admin != null) {
            ProductImg productImg1 = productImgRepo.findImgById(productImg.getId());
            if (productImg1 != null) {
                Product product = productRepo.findProductById(productImg1.getProduct().getId());
                if (product != null) {
                    List<ProductImg> productImgs= productImg1.getProduct().getProductImgs();
                    for (ProductImg productImg2 : productImgs){
                        productImg2.setPrimaryImg(false);
                    }
                    productImgRepo.saveAll(productImgs);
                    productImg1.setPrimaryImg(true);
                    productImgRepo.save(productImg1);
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("product doesn't exist");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("img doesn't exist");

            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}