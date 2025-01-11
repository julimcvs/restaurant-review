package com.julio.restaurant_review.exceptions;

public class BadRequestException extends RuntimeException {
    private Integer errorCode = 400;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
