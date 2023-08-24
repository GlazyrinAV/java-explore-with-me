package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}