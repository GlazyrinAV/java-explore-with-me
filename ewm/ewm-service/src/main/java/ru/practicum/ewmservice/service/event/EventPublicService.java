package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmcommondto.model.EventDto;

import java.util.Collection;

public interface EventPublicService {

    Collection<EventDto> findAll();

    EventDto findById(int id);

}