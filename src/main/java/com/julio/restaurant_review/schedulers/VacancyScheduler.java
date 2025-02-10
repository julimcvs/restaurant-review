package com.julio.restaurant_review.schedulers;

import com.julio.restaurant_review.producers.VacancyEventPublisher;
import com.julio.restaurant_review.services.RestaurantService;
import com.julio.restaurant_review.services.VacancyService;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VacancyScheduler {
    private final VacancyService vacancyService;
    private final RestaurantService restaurantService;
    private final VacancyEventPublisher vacancyEventPublisher;


    public VacancyScheduler(VacancyService vacancyService, RestaurantService restaurantService, VacancyEventPublisher vacancyEventPublisher) {
        this.vacancyService = vacancyService;
        this.restaurantService = restaurantService;
        this.vacancyEventPublisher = vacancyEventPublisher;
    }


    @Scheduled(cron = "0 0 0 * * *")
//    @EventListener(ApplicationReadyEvent.class)
    public void generateDailyVacancies() {
        var restaurants = restaurantService.findAll();
        for (var restaurant : restaurants) {
            vacancyEventPublisher.publishVacancyGenerationEvent(restaurant.getId());
        }
    }
}
