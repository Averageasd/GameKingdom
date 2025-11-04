package org.example.storemanagementbestpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.models.Product;
import org.example.storemanagementbestpractice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = {"products"})
    public ResponseEntity<String> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body("Home");
    }

    public ResponseEntity<Void> saveProduct(Product product){
        productService.saveProductDetails(product);
        log.info("Saved product details successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
