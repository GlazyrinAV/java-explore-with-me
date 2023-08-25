package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Event;

import java.util.Collection;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("UPDATE Event AS E SET E.views = E.views + 1 WHERE E.id = :eventId")
    void increaseViews(int eventId);

    @Query("UPDATE  Event AS E SET E.confirmedRequests = E.confirmedRequests + 1 WHERE E.id = :eventId")
    void increaseConfirmedRequests(int eventId);

    Page<Event> findByInitiatorId(int userId, Pageable page);

    Collection<Event> findByCategoryId(int catId);

}