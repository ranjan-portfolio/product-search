package com.ranjan.productsearch.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;

public interface ProductService {

    public ProductDTO insertProduct(ProductDTO productDTO) throws JsonProcessingException;
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException;
    public String deleteProduct(String id) throws ProductNotFoundException,JsonProcessingException;
}
