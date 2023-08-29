package ru.practicum.statsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsclient.dto.StatsDto;
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

    private final StatsRepository repository;

    private final Mapper mapper;

    private final DateTimeFormatter formatter;

    @Override
    @Transactional
    public void saveStats(StatsDto dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    public Collection<ViewStatsDto> findStats(String start, String end, Collection<String> uris, boolean unique) {
        LocalDateTime startDto = LocalDateTime.parse(URLDecoder.decode(start, Charset.defaultCharset()), formatter);
        LocalDateTime endDto = LocalDateTime.parse(URLDecoder.decode(end, Charset.defaultCharset()), formatter);
        if (startDto.isAfter(endDto)) {
            throw new BadParameter("Дата начала не может быть позже конца.");
        }
        if (unique && uris != null) {
            return repository.findUniqueStatsWithUris(start, end, uris);
        } else if (unique) {
            return repository.findUniqueStatsWithoutUris(start, end);
        } else if (uris != null) {
            return repository.findAllStatsWithUris(start, end, uris);
        } else {
            return repository.findAllStatsWithoutUris(start, end);
        }
    }

    @Override
    public Integer findStatsForEwm(int eventId) {
        Integer hits = repository.findStatsForEwm("/events/" + eventId);
        return Objects.requireNonNullElse(hits, 0);
    }

}