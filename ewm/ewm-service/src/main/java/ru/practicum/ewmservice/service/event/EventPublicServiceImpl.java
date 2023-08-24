package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepository repository;

    private final EventMapper mapper;

    @Override
    public Collection<EventDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto findById(int id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EventNotFound(id)));
    }

}