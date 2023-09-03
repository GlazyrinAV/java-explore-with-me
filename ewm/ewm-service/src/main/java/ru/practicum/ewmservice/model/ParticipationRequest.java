package ru.practicum.ewmservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "participation_requests")
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "requester")
    private User requester;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

}