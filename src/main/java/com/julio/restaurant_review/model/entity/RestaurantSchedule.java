package com.julio.restaurant_review.model.entity;

import com.julio.restaurant_review.model.dto.RestaurantScheduleDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "restaurant_schedule")
@Getter
@Setter
@NoArgsConstructor
public class RestaurantSchedule implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime;

    @Column(name = "closing_time", nullable = false)
    private LocalTime closingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_config_id", referencedColumnName = "id")
    private RestaurantConfiguration configuration;

    public static RestaurantSchedule fromDTO(RestaurantScheduleDTO input, RestaurantConfiguration configuration) {
        var output = new RestaurantSchedule();
        output.setDayOfWeek(input.dayOfWeek());
        output.setOpeningTime(input.openingTime());
        output.setClosingTime(input.closingTime());
        output.setConfiguration(configuration);
        return output;
    }
}
