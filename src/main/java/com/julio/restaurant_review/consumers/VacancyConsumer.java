package com.julio.restaurant_review.consumers;

import com.julio.restaurant_review.services.VacancyService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class VacancyConsumer {
    private final VacancyService vacancyService;

    public VacancyConsumer(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Transactional
    @RabbitListener(queues = "vacancy.queue")
    public void generateVacancies(Long restaurantId) {
        vacancyService.generateVacanciesByRestaurantId(restaurantId);
    }
}
