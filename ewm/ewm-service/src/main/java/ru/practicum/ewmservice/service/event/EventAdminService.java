package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmcommondto.model.EventDto;

import java.util.Collection;

public interface EventAdminService {

    Collection<EventDto> findAll();

    EventDto update(EventDto dto, int id);

}