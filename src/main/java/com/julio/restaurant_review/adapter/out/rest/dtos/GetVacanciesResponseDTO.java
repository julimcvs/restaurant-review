package com.julio.restaurant_review.adapter.out.rest.dtos;

import java.util.Set;

public record GetVacanciesResponseDTO(
        Set<String> vacancies
) {
}
