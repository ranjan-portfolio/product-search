package com.ranjan.productsearch.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ranjan.productsearch.entity.ProductElasticSearch;



public interface ProductSearchRepository extends ElasticsearchRepository<ProductElasticSearch,String>{
        @Query("{\"bool\": {\"should\": ["
         + "{\"fuzzy\": {\"productName\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}},"
         + "{\"fuzzy\": {\"productDescription\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}},"
         + "{\"fuzzy\": {\"brandName\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}},"
         + "{\"fuzzy\": {\"category\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}"
         + "], \"minimum_should_match\": 1}}")
        Page<ProductElasticSearch> getProducts(String searchquery,Pageable page);
    
} 