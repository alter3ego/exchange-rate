package com.exchange.rate.feign.model.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
