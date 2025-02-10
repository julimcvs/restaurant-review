package com.julio.restaurant_review.model.dto;

import com.julio.restaurant_review.exceptions.BadRequestException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record FindVacanciesRequestDTO(
        @NotBlank @Min(0) @Max(100) Integer tableFor,
        @NotBlank LocalDate date
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FindVacanciesRequestDTO(Integer tableFor, String date) {
        this(tableFor, parseDate(date));
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Expected dd/MM/yyyy.");
        }
    }
}
