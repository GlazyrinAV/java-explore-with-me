package ru.practicum.ewmservice.service.event;

import ru.practicum.ewmcommondto.model.*;

import java.util.Collection;

public interface EventPrivateService {

    EventDto save(EventDto dto, int userid);

    Collection<EventDto> findAllByUserId(int userId);

    EventDto findById(int userId, int eventId);

    Collection<ParticipationRequestDto> findRequests(int userId, int eventId);

    EventDto update(EventDto dto, int userId, int eventId);

    EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId);

}