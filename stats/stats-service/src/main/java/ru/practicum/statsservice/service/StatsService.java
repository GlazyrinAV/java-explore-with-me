package ru.practicum.statsservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.statsclient.dto.ViewStatsDto;

import java.util.Collection;

public interface StatsService {

    void saveStats(JsonNode dto);

    Collection<ViewStatsDto> findStats(String start, String end, Collection<String> uris, boolean unique);

    void updateViews();

}