package ru.practicum.ewmservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MarkId implements Serializable {

    public MarkId(Integer userId, Integer eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public MarkId() {

    }

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "event_id", nullable = false)
    private Integer eventId;

}