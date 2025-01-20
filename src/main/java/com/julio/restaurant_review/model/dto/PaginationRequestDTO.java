package com.julio.restaurant_review.model.dto;

public record PaginationRequestDTO(
        Integer page,
        Integer size,
        String sort,
        String direction
) {
    public PaginationRequestDTO {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 10 : size;
        sort = (sort == null || sort.isBlank()) ? "id" : sort;
        direction = (direction == null || direction.isBlank()) ? "ASC" : direction;
    }
}
