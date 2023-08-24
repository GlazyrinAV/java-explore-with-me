package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("UPDATE Event AS E SET E.views = E.views + 1 WHERE E.id = :eventId")
    void increaseViews(int eventId);

}