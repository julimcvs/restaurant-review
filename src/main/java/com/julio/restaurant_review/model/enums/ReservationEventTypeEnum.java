package com.julio.restaurant_review.model.enums;

public enum ReservationEventTypeEnum {
    CREATED,
    CONFIRMED,
    CANCELED;

    public String toString() {
        return name().toLowerCase();
    }
}
