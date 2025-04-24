package com.julio.restaurant_review.domain.enums;

public enum ReservationEventTypeEnum {
    CREATED,
    CONFIRMED,
    CANCELED;

    public String toString() {
        return name().toLowerCase();
    }
}
