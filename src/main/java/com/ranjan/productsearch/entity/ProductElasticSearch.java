package com.ranjan.productsearch.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    @Field(type=FieldType.Text)
    private String productName;
    @Field(type=FieldType.Text)
    private String productDescription;
    @Field(type=FieldType.Text)
    private String brandName;
    private BigDecimal price;
    @Field(type=FieldType.Text)
    private String category;
    private int quantityAvailable;
    
}
