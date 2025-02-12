package com.julio.restaurant_review.model.dto;

import java.util.Set;

public record FindVacanciesResponseDTO(
        Set<String> vacancies
) {
}
