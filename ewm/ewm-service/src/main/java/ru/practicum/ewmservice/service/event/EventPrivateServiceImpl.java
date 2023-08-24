package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.exceptions.ParticipationRequestNotFound;
import ru.practicum.ewmcommondto.exceptions.UserNotFound;
import ru.practicum.ewmcommondto.exceptions.WrongParametr;
import ru.practicum.ewmcommondto.model.*;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository repository;

    private final UserRepository userRepository;

    private final ParticipationRequestsRepository requestsRepository;

    private final EventMapper mapper;

    private final CategoryMapper categoryMapper;

    private final LocationMapper locationMapper;

    private final ParticipationRequestsMapper requestsMapper;

    @Override
    public EventDto save(EventDto dto, int userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        return mapper.toDto(repository.save(mapper.fromDto(dto)));
    }

    @Override
    public Collection<EventDto> findAllByUserId(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        return repository.findByInitiatorId(userId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto findById(int userId, int eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        repository.increaseViews(eventId);
        return mapper.toDto(event);
    }

    @Override
    public Collection<ParticipationRequestDto> findRequests(int userId, int eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        return requestsRepository.findByEventId(eventId).stream().map(requestsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto update(EventDto dto, int userId, int eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!dto.getAnnotation().isBlank()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryMapper.fromDto(dto.getCategory()));
        }
        if (!dto.getDescription().isBlank()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getLocation() != null) {
            event.setLocation(locationMapper.fromDto(dto.getLocation()));
        }
        event.setPaid(dto.isPaid());
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        event.setRequestModeration(dto.isRequestModeration());
        if (!dto.getTitle().isBlank()) {
            event.setTitle(dto.getTitle());
        }
        if (StateAction.from(dto.getStateAction()).isPresent()) {
            if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            } else if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            } else {
                throw new WrongParametr("Указано недопустимое состояние.");
            }
        }
        return mapper.toDto(repository.save(event));
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (RequestStatus.from(dto.getStatus()).isPresent()) {
            if (RequestStatus.valueOf(dto.getStatus()).equals(RequestStatus.CONFIRMED)) {
                for (int requestId : dto.getRequestIds()) {
                    ParticipationRequest request = requestsRepository.findById(requestId)
                            .orElseThrow(() -> new ParticipationRequestNotFound(requestId));
                    request.setStatus(RequestStatus.CONFIRMED);
                    result.getConfirmedRequests().add(requestsMapper.toDto(request));
                }
            } else if (RequestStatus.valueOf(dto.getStatus()).equals(RequestStatus.REJECTED)) {
                for (int requestId : dto.getRequestIds()) {
                    ParticipationRequest request = requestsRepository.findById(requestId)
                            .orElseThrow(() -> new ParticipationRequestNotFound(requestId));
                    request.setStatus(RequestStatus.REJECTED);
                    result.getRejectedRequests().add(requestsMapper.toDto(request));
                }
            } else {
                throw new WrongParametr("Указано недопустимое состояние.");
            }
        }
        return result;
    }

}