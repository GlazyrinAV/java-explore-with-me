package ru.practicum.ewmservice.model;

import java.util.Optional;

public enum SortType {

    EVENT_DATE, VIEWS, MARKS;

    public static Optional<SortType> from(String sort) {
        for (SortType state : values()) {
            if (state.name().equalsIgnoreCase(sort)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}