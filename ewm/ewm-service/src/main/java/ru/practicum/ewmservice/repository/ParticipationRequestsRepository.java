package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.ParticipationRequest;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Integer> {
}