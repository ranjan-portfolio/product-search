package com.ranjan.productsearch.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



public interface ProductController {
    public ResponseEntity<ProductDTO> insertProduct(@Valid ProductDTO productDTO) throws JsonProcessingException;
    public ResponseEntity<ProductDTO> updateProduct(@Valid ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException;
    public ResponseEntity<String> deleteProduct(@NotBlank String id) throws ProductNotFoundException,JsonProcessingException;
    public ResponseEntity<Page<ProductDTO>> search(
    @RequestParam @NotBlank String query,
    @RequestParam @Min(0) int page,
    @RequestParam @Min(1) int size,
    @RequestParam(defaultValue="price") @Pattern(regexp = "^(price|productName|brandName)$") String sortBy,
    @RequestParam(defaultValue="desc") @Pattern(regexp = "^(desc|asc)$") String order);

}
