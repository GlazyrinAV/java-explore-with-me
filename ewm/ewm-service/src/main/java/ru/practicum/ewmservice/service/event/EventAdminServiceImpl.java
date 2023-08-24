package ru.practicum.ewmservice.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository repository;

    @Override
    public Collection<EventDto> findAll() {
        return null;
    }

    @Override
    public EventDto update(EventDto dto, int id) {
        return null;
    }

}