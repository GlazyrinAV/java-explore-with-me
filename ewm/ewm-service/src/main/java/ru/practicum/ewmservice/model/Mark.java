package ru.practicum.ewmservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "marks")
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Mark {

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    private Event event;

    @Column(name = "mark")
    private Integer mark;

    @EmbeddedId
    private MarkId markId;

}