package com.ranjan.productsearch.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "product-topic")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductElasticSearch {

    @Id
    private String id;
    private String productName;
    private String productDescription;
    private String brandName;
    private BigDecimal price;
    private String category;
    private int quantityAvailable;
    
}
