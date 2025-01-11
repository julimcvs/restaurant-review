package com.julio.restaurant_review.exceptions;

public class NotFoundException extends RuntimeException {
    private Integer errorCode = 404;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
