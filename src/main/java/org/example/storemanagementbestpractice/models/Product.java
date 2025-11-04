package org.example.storemanagementbestpractice.models;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class Product {
    private final UUID id;

    @NotBlank(message = "product name cannot be empty")
    @Size(max = 100, message = "product name cannot exceed 100 characters")
    private final String prodName;

    @NotBlank(message = "product description cannot be empty")
    @Size(min = 1, max = 250, message = "product description cannot exceed 250 characters")
    private final String prodDesc;

    @NotNull(message = "product price cannot be empty")
    @Positive(message = "product price has to be greater than 0")
    @Max(value = 1000, message = "product price cannot exceed 1000")
    private final double prodPrice;
}
