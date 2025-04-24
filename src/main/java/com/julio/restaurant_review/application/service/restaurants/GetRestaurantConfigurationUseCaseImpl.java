package com.julio.restaurant_review.application.service.restaurants;

import com.julio.restaurant_review.application.port.in.usecases.restaurants.FindRestaurantByIdUseCase;
import com.julio.restaurant_review.application.port.in.usecases.restaurants.GetRestaurantConfigurationUseCase;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantConfigurationDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.RestaurantScheduleDTO;
import com.julio.restaurant_review.adapter.in.rest.dtos.TableSettingDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GetRestaurantConfigurationUseCaseImpl implements GetRestaurantConfigurationUseCase {
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    public GetRestaurantConfigurationUseCaseImpl(FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
    }

    @Override
    public RestaurantConfigurationDTO execute(Long id) {
        var restaurant = findRestaurantByIdUseCase.execute(id);
        var configuration = restaurant.getConfiguration();
        if (configuration == null) {
            return null;
        }
        return new RestaurantConfigurationDTO(
                configuration.getVacancyInterval(),
                configuration
                        .getSchedules()
                        .stream()
                        .map(schedule -> new RestaurantScheduleDTO(schedule.getDayOfWeek(), schedule.getOpeningTime(), schedule.getClosingTime()))
                        .collect(Collectors.toSet()),
                configuration
                        .getTableSettings()
                        .stream()
                        .map(tableSetting -> new TableSettingDTO(tableSetting.getTableFor(), tableSetting.getVacancyAmount()))
                        .collect(Collectors.toSet())
        );
    }
}
