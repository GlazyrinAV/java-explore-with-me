package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmclient.model.*;

import java.util.Collection;

public interface EventPrivateService {

    EventDto save(NewEventDto dto, int userid);

    Collection<EventDto> findAllByUserId(int userId, int from, int size);

    EventDto findById(int userId, int eventId);

    Collection<ParticipationRequestDto> findRequests(int userId, int eventId);

    EventDto update(UpdateEventUserRequest dto, int userId, int eventId);

    EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId);

}