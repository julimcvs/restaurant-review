package com.julio.restaurant_review.application.service;

import com.julio.restaurant_review.domain.model.RestaurantConfiguration;
import com.julio.restaurant_review.domain.exceptions.BadRequestException;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class VacancyValidator {
    public void validate(Integer tableFor, RestaurantConfiguration configuration, Integer reservationCount) {
        var tableSetting = configuration.getTableSettings()
                .stream()
                .filter(setting -> setting.getTableFor() == tableFor)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("A vacancy with the specified settings was not found."));

        if (tableSetting.getVacancyAmount() <= reservationCount) {
            throw new BadRequestException("Not enough vacancies for the specified settings.");
        }
    }
}
