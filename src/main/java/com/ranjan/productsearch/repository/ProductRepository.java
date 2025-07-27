package com.ranjan.productsearch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ranjan.productsearch.entity.ProductEntity;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity,String> {
    
}
