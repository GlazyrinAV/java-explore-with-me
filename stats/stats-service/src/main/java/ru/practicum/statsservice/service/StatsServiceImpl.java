package ru.practicum.statsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statscommondto.StatsDto;
import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statsservice.exception.BadParameter;
import ru.practicum.statsservice.model.Mapper;
import ru.practicum.statsservice.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    private final Mapper mapper;

    @Override
    public void saveStats(StatsDto dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    public Collection<ViewStatsDto> findStats(String start, String end, Collection<String> uris, boolean unique) {
        LocalDateTime startDto = LocalDateTime.parse(URLDecoder.decode(start, Charset.defaultCharset()));
        LocalDateTime endDto = LocalDateTime.parse(URLDecoder.decode(end, Charset.defaultCharset()));
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
        if (hits != null) {
            return hits;
        } else {
            return 0;
        }
    }

}