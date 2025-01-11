package com.julio.restaurant_review.exceptions;

public class ForbiddenException extends RuntimeException {
    private Integer errorCode = 403;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
