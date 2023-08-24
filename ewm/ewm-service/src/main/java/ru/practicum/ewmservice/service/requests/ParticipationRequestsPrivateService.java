package ru.practicum.ewmservice.service.requests;

import ru.practicum.ewmcommondto.model.ParticipationRequestDto;

import java.util.Collection;

public interface ParticipationRequestsPrivateService {

    ParticipationRequestDto save(int usersId, int eventId);

    Collection<ParticipationRequestDto> findAll(int usersId);

    ParticipationRequestDto cancel(int usersId, int requestId);

}