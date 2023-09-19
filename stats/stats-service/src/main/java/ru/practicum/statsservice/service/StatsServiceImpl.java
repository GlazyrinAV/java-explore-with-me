package ru.practicum.statsservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsclient.dto.ViewStatsDto;
import ru.practicum.statsservice.exception.BadParameter;
import ru.practicum.statsservice.model.Mapper;
import ru.practicum.statsservice.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final
    StatsRepository repository;

    private final Mapper mapper;

    private final DateTimeFormatter formatter;

    @Override
    @Transactional
    public void saveStats(JsonNode dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    public Collection<ViewStatsDto> findStats(String start, String end, Collection<String> uris, boolean unique) {
        LocalDateTime startDto = LocalDateTime.parse(URLDecoder.decode(start, Charset.defaultCharset()), formatter);
        LocalDateTime endDto = LocalDateTime.parse(URLDecoder.decode(end, Charset.defaultCharset()), formatter);
        if (startDto.isAfter(endDto)) {
            throw new BadParameter("Дата начала не может быть позже конца.");
        }
        if (uris != null && !unique) {
            return repository.findAllStatsWithUris(start, end, uris);
        } else if (uris == null && !unique){
            return repository.findAllStatsWithoutUris(start, end);
        } else if (uris == null) {
            return repository.findAllUniqueStatsWithoutUris(start, end);
        } else {
            return repository.findAllUniqueStatsWithUris(start, end, uris);
        }
    }

    @Override
    public Integer findStatsForEwm(int eventId) {
        Integer hits = repository.findStatsForEwm("/events/" + eventId);
        return Objects.requireNonNullElse(hits, 0);
    }

}