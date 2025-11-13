package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.models.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Service
@RequestScope
public class ProductService {

    private int counter = 0;

    public boolean saveProductDetails(ProductEntity product) {
        boolean isSaved = true;
        log.info(product.toString());
        return isSaved;
    }

    public void increaseCounter(){
        counter++;
    }

    public void printCounter(){
        log.info("  value of counter is: {}", counter);
    }
}
