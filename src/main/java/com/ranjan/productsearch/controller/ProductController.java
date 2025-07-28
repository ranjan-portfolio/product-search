package com.ranjan.productsearch.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;



public interface ProductController {
    public ResponseEntity<ProductDTO> insertProduct(ProductDTO productDTO) throws JsonProcessingException;
    public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException;
    public ResponseEntity<String> deleteProduct(String id) throws ProductNotFoundException,JsonProcessingException;
    public ResponseEntity<Page<ProductDTO>> search(String query,int page,int size,String sortBy,String order) ;

}
