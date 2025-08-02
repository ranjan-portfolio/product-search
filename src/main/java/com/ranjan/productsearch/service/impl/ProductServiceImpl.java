package com.ranjan.productsearch.service.impl;

import java.time.Instant;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.entity.OutboxEntity;
import com.ranjan.productsearch.entity.ProductElasticSearch;
import com.ranjan.productsearch.entity.ProductEntity;
import com.ranjan.productsearch.exception.ProductNotFoundException;
import com.ranjan.productsearch.repository.OutboxRepository;
import com.ranjan.productsearch.repository.ProductRepository;
import com.ranjan.productsearch.repository.ProductSearchRepository;
import com.ranjan.productsearch.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

   
    private final ProductRepository productRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final ProductSearchRepository searchRepository;

    @Override
    @Transactional
    public ProductDTO insertProduct(ProductDTO productDTO) throws JsonProcessingException {
        
            ProductEntity entity=convertProductDTOToEntity(productDTO);
            ProductEntity savedEntity=productRepository.insert(entity);
            OutboxEntity event=OutboxEntity.builder()
                                .aggregateType("Product")
                                .aggregateId(savedEntity.getId())
                                .type("ProductCreated")
                                .payload(objectMapper.writeValueAsString(savedEntity))
                                .timestamp(Instant.now())
                                .status(OutboxEntity.EventStatus.PENDING)
                                .build();
           outboxRepository.insert(event);
           ProductDTO dto=convertProductEntitytoDTO(savedEntity);

        
        return dto ;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException {
        
            Optional<ProductEntity> entityOpts =productRepository.findById(productDTO.getId());
            if(!entityOpts.isPresent()){
                throw new ProductNotFoundException("No product available");
             }
             // Update fields
            ProductEntity entity=entityOpts.get();
            entity.setProductName(productDTO.getProductName());
            entity.setProductDescription(productDTO.getProductDescription());
            entity.setCategory(productDTO.getCategory());
            entity.setBrandName(productDTO.getBrandName());
            entity.setPrice(productDTO.getPrice());
            entity.setQuantityAvailable(productDTO.getQuantityAvailable());

            ProductEntity updatedProduct=productRepository.save(entity);
            OutboxEntity event=OutboxEntity.builder()
                                .aggregateType("Product")
                                .aggregateId(updatedProduct.getId())
                                .type("ProductUpdated")
                                .payload(objectMapper.writeValueAsString(updatedProduct))
                                .timestamp(Instant.now())
                                .status(OutboxEntity.EventStatus.PENDING)
                                .build();
           outboxRepository.save(event);
           ProductDTO updatedDTO=convertProductEntitytoDTO(updatedProduct);

        return updatedDTO ;
    }

    @Override
    @Transactional
    public String deleteProduct(String id) throws ProductNotFoundException,JsonProcessingException{
      
            Optional<ProductEntity> entity=productRepository.findById(id);
            if(!entity.isPresent()){
                throw new ProductNotFoundException("No product available");
            }
                productRepository.delete(entity.get());
                OutboxEntity event=OutboxEntity.builder()
                                .aggregateType("Product")
                                .aggregateId(id)
                                .type("ProductDeleted")
                                .payload(objectMapper.writeValueAsString(entity.get()))
                                .timestamp(Instant.now())
                                .status(OutboxEntity.EventStatus.PENDING)
                                .build();
              outboxRepository.save(event);
             
        
        return "Product deleted successfully";
    }

    private ProductEntity convertProductDTOToEntity(ProductDTO productDTO){
        ProductEntity entity=new ProductEntity();
        if(productDTO.getId()!=null){
            entity.setId(productDTO.getId());
        }
        entity.setProductName(productDTO.getProductName());
        entity.setProductDescription(productDTO.getProductDescription());
        entity.setCategory(productDTO.getCategory());
        entity.setBrandName(productDTO.getBrandName());
        entity.setPrice(productDTO.getPrice());
        entity.setQuantityAvailable(productDTO.getQuantityAvailable());
        return entity;
    }

    private ProductDTO convertProductEntitytoDTO(ProductEntity entity){
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(entity.getId());
        productDTO.setProductName(entity.getProductName());
        productDTO.setProductDescription(entity.getProductDescription());
        productDTO.setBrandName(entity.getBrandName());
        productDTO.setCategory(entity.getCategory());
        productDTO.setPrice(entity.getPrice());
        productDTO.setQuantityAvailable(entity.getQuantityAvailable());
        return productDTO;
    }

     private ProductDTO convertProductSearchEntitytoDTO(ProductElasticSearch entity){
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(entity.getId());
        productDTO.setProductName(entity.getProductName());
        productDTO.setProductDescription(entity.getProductDescription());
        productDTO.setBrandName(entity.getBrandName());
        productDTO.setCategory(entity.getCategory());
        productDTO.setPrice(entity.getPrice());
        productDTO.setQuantityAvailable(entity.getQuantityAvailable());
        return productDTO;
    }

    @Override
    public Page<ProductDTO> search(String query, int pageNum, int size, String sortBy, String orderBy) {
            Sort sort=Sort.by(Sort.Direction.fromString(orderBy),sortBy);
            Pageable page=PageRequest.of(pageNum, size,sort);
            Page<ProductElasticSearch> results=searchRepository.getProducts(query, page);
            Page<ProductDTO> searchResult=results.map(this::convertProductSearchEntitytoDTO);
            return searchResult;
    }
    
}
