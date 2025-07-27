package com.ranjan.productsearch.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProductDTO {
    private String id;
    @NotBlank(message = "Product name cannot be blank")
    @Size(max=100,message="Product name should not exceed 100 characters")
    private String productName;
    @Size(max=10000,message="Description should not exceed 10000 characters")
    private String productDescription;
    @NotBlank(message = "Product brand name cannot be blank")
    private String brandName;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private BigDecimal price;
    @NotBlank(message="Product category cannot be null")
    private String category;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantityAvailable;
    
}
