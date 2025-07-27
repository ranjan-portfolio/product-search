package com.ranjan.productsearch.exception;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(String msg){
        super(msg);
    }

    public ProductNotFoundException(String msg,Throwable th){
        super(msg,th);
    }
    
}
