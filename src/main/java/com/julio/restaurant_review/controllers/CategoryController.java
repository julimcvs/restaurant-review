package com.julio.restaurant_review.controllers;

import com.julio.restaurant_review.model.dto.CategoryListDTO;
import com.julio.restaurant_review.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CategoryListDTO[]> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
