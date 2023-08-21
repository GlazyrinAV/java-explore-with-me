package ru.practicum.statsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statscommondto.model.dto.ViewStats;
import ru.practicum.statscommondto.model.dto.StatsDto;
import ru.practicum.statsservice.model.Mapper;
import ru.practicum.statsservice.repository.StatsRepository;

import java.time.LocalDateTime;

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
    public ViewStats findStats(LocalDateTime start, LocalDateTime end, String uri, Boolean unique) {
        return null;
    }

}