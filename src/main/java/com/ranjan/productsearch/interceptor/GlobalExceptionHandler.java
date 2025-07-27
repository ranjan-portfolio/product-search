package com.ranjan.productsearch.interceptor;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ranjan.productsearch.dto.ErrorDTO;
import com.ranjan.productsearch.exception.ProductNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFound(ProductNotFoundException productNotFoundException){
        log.debug("Inside productNotFoundException advise");
        ErrorDTO error=new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Product Not Found", productNotFoundException.getMessage(), null,Instant.now());
        return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
       log.debug("Inside validation exception advise");

       List<FieldError> fieldErrors=  ex.getFieldErrors();
       StringBuffer message=new StringBuffer();
       for(FieldError fieldError:fieldErrors){
            message.append("Field '")
               .append(fieldError.getField())
               .append("' - ")
               .append(fieldError.getDefaultMessage())
               .append("; ");
       }

       if (message.length() > 0) {
        message.setLength(message.length() - 2);
       }

       ErrorDTO error=new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Validation error", message.toString(), null,Instant.now());
       return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorDTO> handleJsonProcessingException(JsonProcessingException jsonProcessingException){
         log.debug("Inside json procesing exception advise");
         ErrorDTO error=new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Validation error", "BAD Request body", null,Instant.now());
        return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
        log.debug("Inside general exception");
        ErrorDTO error=new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception occured", "Internal server error", null,Instant.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
