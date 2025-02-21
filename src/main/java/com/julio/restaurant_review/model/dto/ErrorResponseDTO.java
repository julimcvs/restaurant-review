package com.julio.restaurant_review.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private String message;
    private Integer errorCode;

    public String getMessage() {
        return message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}