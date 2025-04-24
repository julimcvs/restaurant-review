package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.reservations.CountReservationsByRestaurantUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.GetRestaurantVacanciesUseCase;
import com.julio.restaurant_review.domain.model.RestaurantConfiguration;
import com.julio.restaurant_review.domain.model.RestaurantSchedule;
import com.julio.restaurant_review.domain.model.TableSetting;
import com.julio.restaurant_review.domain.exceptions.BadRequestException;
import com.julio.restaurant_review.domain.exceptions.NotFoundException;
import com.julio.restaurant_review.adapter.in.rest.dtos.FindVacanciesRequestDTO;
import com.julio.restaurant_review.adapter.out.rest.dtos.GetVacanciesResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class GetRestaurantVacanciesUseCaseImpl implements GetRestaurantVacanciesUseCase {
    private final CountReservationsByRestaurantUseCase countReservationsByRestaurantUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    public GetRestaurantVacanciesUseCaseImpl(CountReservationsByRestaurantUseCase countReservationsByRestaurantUseCase, FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        this.countReservationsByRestaurantUseCase = countReservationsByRestaurantUseCase;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
    }

    public static Set<String> generateTimeSet(LocalDate inputDate, RestaurantSchedule todaySchedule, Integer intervalMinutes) {
        LinkedHashSet<String> timeSet = new LinkedHashSet<>();
        LocalTime now = LocalTime.now();
        LocalTime openingTime = todaySchedule.getOpeningTime();
        LocalTime closingTime = todaySchedule.getClosingTime();
        LocalTime startTime = openingTime;

        // If the input scheduledDate is today, set the start time to the next valid interval after now
        if (inputDate.isEqual(LocalDate.now())) {
            Integer minutesSinceOpening = (int) now.until(openingTime, java.time.temporal.ChronoUnit.MINUTES);
            if (minutesSinceOpening > 0) {
                // If current time is before opening, start at opening time
                startTime = openingTime;
            } else {
                // Find the next valid interval
                int minutesPastInterval = now.getMinute() % intervalMinutes;
                int minutesToNextInterval = intervalMinutes - minutesPastInterval;
                startTime = now.plusMinutes(minutesToNextInterval).withSecond(0).withNano(0);

                // Ensure startTime is not before openingTime
                if (startTime.isBefore(openingTime)) {
                    startTime = openingTime;
                }
            }
        }
        var startDate = LocalDate.now().atTime(startTime);
        var closingDate = LocalDate.now().atTime(closingTime);

        // Generate time slots in intervals of `intervalMinutes`
        for (LocalDateTime date = startDate; !date.isAfter(closingDate); date = date.plusMinutes(intervalMinutes)) {
            var time = date.toLocalTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Format without seconds
            timeSet.add(time.format(formatter));
        }

        return timeSet;
    }

    private static void validateTableSetting(FindVacanciesRequestDTO input, Set<TableSetting> tableSettings, Integer reservationCount) {
        var tableSetting = tableSettings
                .stream()
                .filter(setting -> setting.getTableFor() == input.tableFor())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("A vacantion with the specified settings was not found."));
        if (tableSetting.getVacancyAmount() <= reservationCount) {
            throw new BadRequestException("Not enough vacancies for the specified settings.");
        }
    }

    @Override
    public GetVacanciesResponseDTO execute(Long id, FindVacanciesRequestDTO input) {
        var config = getRestaurantConfiguration(id);
        var reservationCount = this.countReservationsByRestaurantUseCase.execute(id, input.tableFor(), input.date());
        var tableSettings = config.getTableSettings();
        validateTableSetting(input, tableSettings, reservationCount);
        var dateSchedule = config.getSchedules()
                .stream()
                .filter(schedule -> schedule.getDayOfWeek() == input.date().getDayOfWeek())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Restaurant is not opened in the specified scheduledDate."));
        var timeSet = generateTimeSet(input.date(), dateSchedule, config.getVacancyInterval());
        return new GetVacanciesResponseDTO(timeSet);
    }

    private RestaurantConfiguration getRestaurantConfiguration(Long id) {
        var config = this.findRestaurantByIdUseCase.execute(id).getConfiguration();
        if (config == null) {
            throw new BadRequestException("Restaurant not configured yet.");
        }
        return config;
    }
}
