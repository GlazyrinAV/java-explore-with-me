package ru.practicum.ewmservice.model;

import java.util.Optional;

public enum StateAction {

    SEND_TO_REVIEW, CANCEL_REVIEW, PUBLISH_EVENT, REJECT_EVENT;

    public static Optional<StateAction> from(String stateAction) {
        for (StateAction state : values()) {
            if (state.name().equalsIgnoreCase(stateAction)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}