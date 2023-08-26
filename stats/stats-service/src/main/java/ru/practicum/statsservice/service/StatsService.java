package ru.practicum.statsservice.service;

import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statscommondto.StatsDto;

import java.util.Collection;

public interface StatsService {

    void saveStats(StatsDto dto);

    Collection<ViewStatsDto> findStats(String start, String end, Collection<String> uris, boolean unique);

    Integer findStatsForEwm(int eventId);

}