package com.ranjan.productsearch.service;



import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;

public interface ProductService {
    public Page<ProductDTO> search(String query,int page,int size,String sortBy,String orderBy);
    public ProductDTO insertProduct(ProductDTO productDTO) throws JsonProcessingException;
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException;
    public String deleteProduct(String id) throws ProductNotFoundException,JsonProcessingException;
}
