package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.EventState;

public interface StateRepository extends JpaRepository<EventState, Integer> {
}