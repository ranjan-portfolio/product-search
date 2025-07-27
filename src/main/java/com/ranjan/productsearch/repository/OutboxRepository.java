package com.ranjan.productsearch.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ranjan.productsearch.entity.OutboxEntity;
@Repository
public interface OutboxRepository extends MongoRepository<OutboxEntity,String> {
    // Find all events by status
    List<OutboxEntity> findByStatusIn(List<OutboxEntity.EventStatus> status);
    
} 
