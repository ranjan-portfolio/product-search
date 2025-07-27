package com.ranjan.productsearch.controller;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;



public interface ProductController {
    public ResponseEntity<ProductDTO> insertProduct(ProductDTO productDTO) throws JsonProcessingException;
    public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException;
    public ResponseEntity<String> deleteProduct(String id) throws ProductNotFoundException,JsonProcessingException;

}
