package org.example.storemanagementbestpractice.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Product {
    private final UUID id;
    private final String prodName;
    private final String prodDesc;
    private final double prodPrice;
}
