package com.julio.restaurant_review.services;

import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.model.dto.FindVacanciesRequestDTO;
import com.julio.restaurant_review.model.entity.Restaurant;
import com.julio.restaurant_review.model.entity.Vacancy;
import com.julio.restaurant_review.model.enums.VacancyStatusEnum;
import com.julio.restaurant_review.repositories.VacancyRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VacancyService {
    private final VacancyRepository repository;
    private final RestaurantService restaurantService;

    public VacancyService(VacancyRepository repository, @Lazy RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    private static List<Vacancy> getVacancies(List<LocalDateTime> times, Restaurant restaurant) {
        List<Vacancy> vacancies = new ArrayList<>();
        times.forEach(time -> {
            // TODO Quantidade de pessoas por mesa deve ser configurável por restaurante
            Integer[] tableFor = {2, 4, 6, 8};
            for (Integer tableSize : tableFor) {
                var vacancy = new Vacancy();
                vacancy.setRestaurant(restaurant);
                vacancy.setStatus(VacancyStatusEnum.AVAILABLE);
                vacancy.setTimeSlotStart(time);
                vacancy.setTimeSlotEnd(time.plusHours(1));
                vacancy.setTableFor(tableSize);
                vacancies.add(vacancy);
            }
        });
        return vacancies;
    }

    private static List<LocalDateTime> getVacancyTimes(List<LocalDate> daysToFill) {
        List<LocalDateTime> times = new ArrayList<>();
        daysToFill.forEach(day -> {
            // TODO Horário de funcionamento deve ser configurável por restaurante
            for (int i = 18; i < 23; i++) {
                // TODO Intervalo deve ser configurável por restaurante
                times.add(day.atTime(i, 0));
            }
        });
        return times;
    }

    private Boolean existsVacanciesByRestaurantId(Long restaurantId) {
        return repository.existsByRestaurantId(restaurantId);
    }

    public Set<LocalDate> findDatesByRestaurantId(Long restaurantId) {
        return repository.findDistinctDatesByRestaurantId(restaurantId, LocalDate.now().atStartOfDay())
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Vacancy findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Vacancy not found"));
    }

    public Set<Vacancy> findByRestaurantId(Long restaurantId, FindVacanciesRequestDTO query, VacancyStatusEnum status) {
        return repository.findByRestaurantIdAndStatusAndTableForAndTimeSlotStartAfterOrderByTimeSlotStart(restaurantId, status, query.tableFor(), query.date().atStartOfDay());
    }

    public void generateVacanciesByRestaurantId(Long restaurantId) {
        var restaurant = restaurantService.findEntityById(restaurantId);
        var daysToFill = getDaysToFill(restaurantId);
        var times = getVacancyTimes(daysToFill);
        var vacancies = getVacancies(times, restaurant);
        saveAllVacancies(vacancies);
    }

    private List<LocalDate> getDaysToFill(Long restaurantId) {
        var hasVacancies = existsVacanciesByRestaurantId(restaurantId);
        List<LocalDate> daysToFill = new ArrayList<>();
        var date = LocalDate.now();
        if (hasVacancies) {
            // Se já tiver gerado vagas, gerará vagas para daqui há 1 semana
            daysToFill.add(date.plusDays(7));
            return daysToFill;
        }
        // Se não, gerará vagas para todos os dias da próxima semana.
        for (int i = 0; i < 7; i++) {
            daysToFill.add(date.plusDays(i));
        }
        return daysToFill;
    }

    private void saveAllVacancies(List<Vacancy> vacancies) {
        int batchSize = 100;
        for (int i = 0; i < vacancies.size(); i += batchSize) {
            List<Vacancy> batch = vacancies.subList(i, Math.min(i + batchSize, vacancies.size()));
            repository.saveAll(batch);
        }
    }
}
