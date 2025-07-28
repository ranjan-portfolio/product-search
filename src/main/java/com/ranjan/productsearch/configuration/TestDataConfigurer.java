package com.ranjan.productsearch.configuration;


import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.entity.ProductEntity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@ConditionalOnProperty(name="productsearch.fakedataload.required",havingValue = "Y",matchIfMissing = false)
@Component
@Data
@Slf4j
public class TestDataConfigurer implements CommandLineRunner{

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${productsearch.url}")
    private String baseURL;
    
    @Override
    public void run(String... args) throws Exception {
        List<ProductDTO> products = objectMapper.readValue(
            new ClassPathResource("products.json").getInputStream(),
            new TypeReference<List<ProductDTO>>() {}
        );
        sendProducts(products);
    }

    private void sendProducts(List<ProductDTO> products) {
        String fullURL = baseURL+"/product/insert";
        log.info("Test data called configuration called ........... ");
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (ProductDTO prod : products) {
            restTemplate.postForObject(fullURL, new HttpEntity<>(prod, headers), ProductEntity.class);
        }
    }
}
