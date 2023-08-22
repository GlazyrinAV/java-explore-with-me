package ru.practicum.statsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statscommondto.StatsDto;
import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statsservice.model.Mapper;
import ru.practicum.statsservice.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    private final Mapper mapper;

    @Override
    public void saveStats(StatsDto dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    public Collection<ViewStatsDto> findStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
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

}