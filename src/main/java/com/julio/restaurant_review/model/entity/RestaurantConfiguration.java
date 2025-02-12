package com.julio.restaurant_review.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.julio.restaurant_review.model.dto.RestaurantConfigurationDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "restaurant_configuration")
public class RestaurantConfiguration implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vacancy_interval", nullable = false)
    private Integer vacancyInterval;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "configuration", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TableSetting> tableSettings;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "configuration", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RestaurantSchedule> schedules;

    public static RestaurantConfiguration fromDTO(RestaurantConfigurationDTO input) {
        var output = new RestaurantConfiguration();
        output.setVacancyInterval(input.vacancyInterval());
        output.setTableSettings(
                input.tableSettings().stream().map(tableSetting -> TableSetting.fromDTO(tableSetting, output)).collect(Collectors.toSet())
        );
        output.setSchedules(
                input.schedules().stream().map(schedule -> RestaurantSchedule.fromDTO(schedule, output)).collect(Collectors.toSet())
        );
        return output;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVacancyInterval() {
        return vacancyInterval;
    }

    public void setVacancyInterval(Integer vacancyInterval) {
        this.vacancyInterval = vacancyInterval;
    }

    public Set<TableSetting> getTableSettings() {
        return tableSettings;
    }

    public void setTableSettings(Set<TableSetting> tableSettings) {
        this.tableSettings = tableSettings;
    }

    public Set<RestaurantSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<RestaurantSchedule> schedules) {
        this.schedules = schedules;
    }
}
