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

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "mark")
    private Integer mark;

}