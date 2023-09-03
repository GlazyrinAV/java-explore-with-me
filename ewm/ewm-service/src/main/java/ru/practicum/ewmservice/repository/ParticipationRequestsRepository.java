package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.Collection;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Integer> {

    Collection<ParticipationRequest> findByEventId(int eventId);

    @Query("SELECT count(PR.id) FROM ParticipationRequest AS PR WHERE PR.event.id in :eventId AND PR.status = 'CONFIRMED' ")
    int findConfirmedRequests(int eventId);

    Collection<ParticipationRequest> findByRequesterId(int userId);

    boolean existsByRequesterIdAndEventId(int userId, int eventId);

    @Query("FROM ParticipationRequest AS PR WHERE PR.requester.id in :userId " +
            "AND PR.event.id in :eventId " +
            "AND PR.status = 'CONFIRMED' ")
    ParticipationRequest findRequestByUserForMarks(int userId, int eventId);

}