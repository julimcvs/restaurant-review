package com.julio.restaurant_review.adapter.in.rest.dtos;

public record PaginationRequestDTO(
        Integer page,
        Integer size,
        String sort,
        String direction
) {
    public PaginationRequestDTO {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 10 : size;
        sort = (sort == null || sort.isBlank()) ? "restaurantId" : sort;
        direction = (direction == null || direction.isBlank()) ? "ASC" : direction;
    }
}
