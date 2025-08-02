package com.ranjan.productsearch.controller.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.productsearch.controller.ProductController;
import com.ranjan.productsearch.dto.ErrorDTO;
import com.ranjan.productsearch.dto.ProductDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;
import com.ranjan.productsearch.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Product", description = "API for product search")
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController{

   
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping("/search")
    @Operation(summary = "search product", description = "search prowered by elastic search")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "search",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductDTO.class))),
         @ApiResponse(responseCode = "404",description = "Product not found",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Page<ProductDTO>> search(
    @RequestParam @NotBlank String query,
    @RequestParam @Min(0) int page,
    @RequestParam @Min(1) int size,
    @RequestParam(defaultValue="price") @Pattern(regexp = "^(price|productName|brandName)$") String sortBy,
    @RequestParam(defaultValue="desc") @Pattern(regexp = "^(desc|asc)$") String order) {
     Page<ProductDTO> productResponse=productService.search(query, page, size, sortBy, order);
     return ResponseEntity.ok(productResponse);
}

    @Override
    @PostMapping("/insert")
    @Operation(summary = "insert product", description = "Adds product in DB")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Product inserted",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductDTO.class))),
         @ApiResponse(responseCode = "404",description = "Product not found",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<ProductDTO> insertProduct(@Valid @RequestBody ProductDTO productDTO) throws JsonProcessingException {
        log.info("Inside insertProduct...");
        ProductDTO newProduct=productService.insertProduct(productDTO);
        log.debug("New product saved by db is::"+objectMapper.writeValueAsString(newProduct));
        return new ResponseEntity<>(newProduct,HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "update product", description = "Updates product in DB")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Product updated",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductDTO.class))),
         @ApiResponse(responseCode = "404",description = "Product not found",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) throws ProductNotFoundException,JsonProcessingException {
        log.info("Inside updateProduct...");
        ProductDTO updatedProduct=productService.updateProduct(productDTO);
        log.debug("updated product saved by db is::"+objectMapper.writeValueAsString(updatedProduct));
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "delete product", description = "delete product in DB")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Product deleted",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "404",description = "Product not found",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<String> deleteProduct(@NotBlank @RequestParam("id")String id) throws ProductNotFoundException,JsonProcessingException {
        log.info("Inside delete product");
        String message= productService.deleteProduct(id);
        log.debug("Deleted status is"+message);
       return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
}
