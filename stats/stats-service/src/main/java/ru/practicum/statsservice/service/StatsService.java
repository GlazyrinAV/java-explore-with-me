package ru.practicum.statsservice.service;

import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statscommondto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsService {

    void saveStats(StatsDto dto);

    Collection<ViewStatsDto> findStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);

}