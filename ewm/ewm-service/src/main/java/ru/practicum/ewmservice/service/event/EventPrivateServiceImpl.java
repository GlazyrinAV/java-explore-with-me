package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmcommondto.model.EventRequestDto;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository repository;

    @Override
    public EventDto save(EventDto dto, int userid) {
        return null;
    }

    @Override
    public Collection<EventDto> findAllByUserId(int userId) {
        return null;
    }

    @Override
    public EventDto findById(int userId, int eventId) {
        return null;
    }

    @Override
    public Collection<EventRequestDto> findRequests(int userId, int eventId) {
        return null;
    }

    @Override
    public EventDto update(EventDto dto, int userId, int eventId) {
        return null;
    }

    @Override
    public Collection<EventRequestDto> updateRequests(EventDto dto, int userId, int eventId) {
        return null;
    }

}