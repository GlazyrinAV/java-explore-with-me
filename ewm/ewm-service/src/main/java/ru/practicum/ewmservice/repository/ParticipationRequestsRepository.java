package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.Collection;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Integer> {

    Collection<ParticipationRequest> findByEventId(int eventId);

    Collection<ParticipationRequest> findByRequesterId(int userId);

    boolean existsByRequesterIdAndEventId(int userId, int eventId);

}