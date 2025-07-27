package com.ranjan.productsearch.configuration;


import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.productsearch.entity.ProductEntity;

import lombok.Data;

@ConditionalOnProperty(name="product.fakedataload.required",havingValue = "Y",matchIfMissing = false)
@Component
@Data
public class TestDataConfigurer implements CommandLineRunner{

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        List<ProductEntity> products = objectMapper.readValue(
            new ClassPathResource("product.json").getInputStream(),
            new TypeReference<List<ProductEntity>>() {}
        );
        sendProducts(products);
    }

    private void sendProducts(List<ProductEntity> products) {
        String url = "http://localhost:8083/product/insert";
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (ProductEntity prod : products) {
            restTemplate.postForObject(url, new HttpEntity<>(prod, headers), ProductEntity.class);
        }
    }
}
