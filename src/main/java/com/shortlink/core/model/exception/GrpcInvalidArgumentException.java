package com.shortlink.core.model.exception;

public class GrpcInvalidArgumentException extends RuntimeException {

    public GrpcInvalidArgumentException(String message) {
        super(message);
    }
}
