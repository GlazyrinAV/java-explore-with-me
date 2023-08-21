package ru.practicum.statsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.statsservice.model.Stats;

public interface StatsRepository extends JpaRepository<Stats, Integer> {



}