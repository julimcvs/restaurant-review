package com.julio.restaurant_review.exceptions;

public class UnauthorizedException extends RuntimeException {
    private Integer errorCode = 401;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
