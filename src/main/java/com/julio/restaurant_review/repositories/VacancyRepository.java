package com.julio.restaurant_review.repositories;

import com.julio.restaurant_review.model.entity.Vacancy;
import com.julio.restaurant_review.model.enums.VacancyStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Boolean existsByRestaurantId(Long restaurantId);

    @Query("""
            SELECT DISTINCT CAST(v.timeSlotStart AS java.time.LocalDate)
            FROM Vacancy v WHERE v.restaurant.id = :restaurantId AND v.timeSlotStart >= :startTime
            """)
    Set<LocalDate> findDistinctDatesByRestaurantId(@Param("restaurantId") Long restaurantId, @Param("startTime") LocalDateTime startTime);

    Set<Vacancy> findByRestaurantIdAndStatusAndTableForAndTimeSlotStartAfterOrderByTimeSlotStart(Long restaurantId, VacancyStatusEnum status, Integer tableFor, LocalDateTime date);
}
