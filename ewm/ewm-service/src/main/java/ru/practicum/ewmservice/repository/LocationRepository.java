package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}