package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.State;

public interface StateRepository extends JpaRepository<State, Integer> {
}