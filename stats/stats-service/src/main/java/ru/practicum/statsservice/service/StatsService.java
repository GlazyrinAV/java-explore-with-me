package ru.practicum.statsservice.service;

import ru.practicum.statscommondto.model.dto.ViewStats;
import ru.practicum.statscommondto.model.dto.StatsDto;

import java.time.LocalDateTime;

public interface StatsService {

    void saveStats(StatsDto dto);

    ViewStats findStats(LocalDateTime start, LocalDateTime end, String uri, Boolean unique);

}