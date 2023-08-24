package ru.practicum.ewmservice.model;

import java.util.Optional;

public enum RequestStatus {

    PENDING, CONFIRMED, REJECTED;

    public static Optional<RequestStatus> from(String requestStatus) {
        for (RequestStatus state : values()) {
            if (state.name().equalsIgnoreCase(requestStatus)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}