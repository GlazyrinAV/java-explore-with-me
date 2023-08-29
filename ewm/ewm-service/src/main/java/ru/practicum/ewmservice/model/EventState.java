package ru.practicum.ewmservice.model;

import java.util.Optional;

public enum EventState {

    PENDING, PUBLISHED, CANCELED;

    public static Optional<EventState> from(String eventState) {
        for (EventState state : values()) {
            if (state.name().equalsIgnoreCase(eventState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}