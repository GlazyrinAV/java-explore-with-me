package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmclient.model.event.EventDto;
import ru.practicum.ewmclient.model.event.NewEventDto;
import ru.practicum.ewmclient.model.event.UpdateEventUserRequest;
import ru.practicum.ewmclient.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewmclient.model.request.EventRequestStatusUpdateResult;
import ru.practicum.ewmclient.model.request.ParticipationRequestDto;

import java.util.Collection;

public interface EventPrivateService {

    EventDto save(NewEventDto dto, int userid);

    Collection<EventDto> findAllByUserId(int userId, int from, int size);

    EventDto findById(int userId, int eventId);

    Collection<ParticipationRequestDto> findRequests(int userId, int eventId);

    EventDto update(UpdateEventUserRequest dto, int userId, int eventId);

    EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId);

}