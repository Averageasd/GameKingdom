package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.models.Product;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {
    public boolean saveProductDetails(Product product) {
        boolean isSaved = true;
        log.info(product.toString());
        return isSaved;
    }
}
