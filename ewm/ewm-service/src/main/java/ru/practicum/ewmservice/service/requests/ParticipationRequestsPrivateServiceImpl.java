package ru.practicum.ewmservice.service.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exceptions.exceptions.EventNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.ParticipationRequestNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.UserNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmclient.model.ParticipationRequestDto;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationRequestsPrivateServiceImpl implements ParticipationRequestsPrivateService {

    private final ParticipationRequestsRepository repository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final ParticipationRequestsMapper mapper;

    @Override
    public ParticipationRequestDto save(int usersId, int eventId) {
        User user = userRepository.findById(usersId).orElseThrow(() -> new UserNotFound(usersId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new WrongParameter("Нельзя участвовать в неопубликованном событии.");
        }
        if (event.getInitiator().equals(user)) {
            throw new WrongParameter("Инициатор события не может добавить запрос на участие в своём событии.");
        }
        if (repository.existsByRequesterIdAndEventId(usersId, eventId)) {
            throw new WrongParameter("Нельзя добавить повторный запрос.");
        }
        if (event.getParticipantLimit() > 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new WrongParameter("У события достигнут лимит запросов на участие.");
        }

        ParticipationRequest request = ParticipationRequest.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();

        if (event.isRequestModeration() && event.getParticipantLimit() > 0) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            eventRepository.increaseConfirmedRequests(eventId);
            request.setStatus(RequestStatus.CONFIRMED);
        }
        return mapper.toDto(repository.save(request));
    }

    @Override
    public Collection<ParticipationRequestDto> findAll(int usersId) {
        userRepository.findById(usersId).orElseThrow(() -> new UserNotFound(usersId));
        return repository.findByRequesterId(usersId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto cancel(int usersId, int requestId) {
        userRepository.findById(usersId).orElseThrow(() -> new UserNotFound(usersId));
        ParticipationRequest request = repository.findById(requestId).orElseThrow(() -> new ParticipationRequestNotFound(requestId));
        if (request.getRequester().getId() != usersId) {
            throw new WrongParameter("Данный пользователь не создавал данный запрос.");
        }

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            eventRepository.decreaseConfirmedRequests(request.getEvent().getId());
        }
        request.setStatus(RequestStatus.CANCELED);
        return mapper.toDto(repository.save(request));
    }

}