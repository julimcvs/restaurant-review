package com.julio.restaurant_review.model.entity;

import com.julio.restaurant_review.model.enums.ReservationStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="reservation")
public class Reservation implements Serializable {
    @Serial
    public static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "table_for", nullable = false)
    Integer tableFor;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Column(nullable = false)
    private ReservationStatusEnum status;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public Integer getTableFor() {
        return tableFor;
    }

    public void setTableFor(Integer tableFor) {
        this.tableFor = tableFor;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduled_date) {
        this.scheduledDate = scheduled_date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
