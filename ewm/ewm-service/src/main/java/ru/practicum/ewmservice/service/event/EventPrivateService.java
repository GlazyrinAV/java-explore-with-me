package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmcommondto.model.EventRequestDto;

import java.util.Collection;

public interface EventPrivateService {

    EventDto save(EventDto dto, int userid);

    Collection<EventDto> findAllByUserId(int userId);

    EventDto findById(int userId, int eventId);

    Collection<EventRequestDto> findRequests(int userId, int eventId);

    EventDto update(EventDto dto, int userId, int eventId);

    Collection<EventRequestDto> updateRequests(EventDto dto, int userId, int eventId);

}