package com.julio.restaurant_review.handlers;

import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.exceptions.ForbiddenException;
import com.julio.restaurant_review.exceptions.NotFoundException;
import com.julio.restaurant_review.exceptions.UnauthorizedException;
import com.julio.restaurant_review.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleCustomException(BadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(ex.getMessage(), ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleCustomException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(ex.getMessage(), 400));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleCustomException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage(), ex.getErrorCode()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleCustomException(UnauthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(ex.getMessage(), ex.getErrorCode()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleCustomException(ForbiddenException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDTO(ex.getMessage(), ex.getErrorCode()));
    }
}
