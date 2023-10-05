package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmclient.model.event.EventDto;

import java.util.Collection;

public interface EventPublicService {

    Collection<EventDto> findAll(int from,
                                 int size,
                                 String text,
                                 Collection<Integer> categories,
                                 Boolean paid,
                                 String rangeStart,
                                 String rangeEnd,
                                 Boolean onlyAvailable,
                                 String sort);

    EventDto findById(int id);

}