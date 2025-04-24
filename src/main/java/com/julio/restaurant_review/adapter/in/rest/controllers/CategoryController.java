package com.julio.restaurant_review.adapter.in.rest.controllers;

import com.julio.restaurant_review.application.port.in.usecases.categories.FindAllCategoriesUseCase;
import com.julio.restaurant_review.adapter.out.rest.dtos.CategoryListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final FindAllCategoriesUseCase findAllCategoriesUseCase;

    public CategoryController(FindAllCategoriesUseCase findAllCategoriesUseCase) {
        this.findAllCategoriesUseCase = findAllCategoriesUseCase;
    }

    @GetMapping
    public ResponseEntity<CategoryListDTO[]> findAll() {
        return ResponseEntity.ok(findAllCategoriesUseCase.execute());
    }
}
