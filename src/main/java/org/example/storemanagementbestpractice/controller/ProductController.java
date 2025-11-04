package org.example.storemanagementbestpractice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.models.Product;
import org.example.storemanagementbestpractice.services.ProductService;
import org.example.storemanagementbestpractice.util.GenerateErrorMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Slf4j
@Controller
public class ProductController {

    private final ProductService productService;
    private final GenerateErrorMessageUtil generateErrorMessageUtil;

    public ProductController(
            ProductService productService,
            GenerateErrorMessageUtil generateErrorMessageUtil
    ) {
        this.productService = productService;
        this.generateErrorMessageUtil = generateErrorMessageUtil;
    }

    @GetMapping(path = "product")
    public ResponseEntity<Void> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "privateProduct")
    public ResponseEntity<Void> getPrivateProducts() {
        log.info("private products not allowed");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "create/product")
    public ResponseEntity<?> saveProduct(
            @Valid @RequestBody Product product,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.info(generateErrorMessageUtil
                    .getBindingErrorMessages(bindingResult).toString());
            return ResponseEntity.badRequest()
                    .body(
                            generateErrorMessageUtil
                                    .getBindingErrorMessages(bindingResult)
                    );
        }
        productService.saveProductDetails(product);
        log.info("Saved product details successfully");
        productService.increaseCounter();
        productService.printCounter();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
