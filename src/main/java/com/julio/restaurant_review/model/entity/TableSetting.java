package com.julio.restaurant_review.model.entity;

import com.julio.restaurant_review.model.dto.TableSettingDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "table_setting")
@Getter
@Setter
@NoArgsConstructor
public class TableSetting implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "table_for", nullable = false)
    Integer tableFor;

    @Column(name = "vacancy_amount", nullable = false)
    Integer vacancyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_config_id", referencedColumnName = "id")
    private RestaurantConfiguration configuration;

    public static TableSetting fromDTO(TableSettingDTO input, RestaurantConfiguration configuration) {
        var output = new TableSetting();
        output.setTableFor(input.tableFor());
        output.setVacancyAmount(input.vacancyAmount());
        output.setConfiguration(configuration);
        return output;
    }
}
