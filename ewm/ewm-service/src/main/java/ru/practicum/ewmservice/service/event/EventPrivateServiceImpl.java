package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.*;
import ru.practicum.ewmcommondto.model.*;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository repository;

    private final UserRepository userRepository;

    private final ParticipationRequestsRepository requestsRepository;

    private final CategoryRepository categoryRepository;

    private final EventMapper mapper;

    private final LocationMapper locationMapper;

    private final ParticipationRequestsMapper requestsMapper;

    @Override
    public EventDto save(NewEventDto dto, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = mapper.fromDto(dto);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        return mapper.toDto(repository.save(event));
    }

    @Override
    public Collection<EventDto> findAllByUserId(int userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);
        return repository.findByInitiatorId(userId, page).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto findById(int userId, int eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!event.getInitiator().equals(user)) {
            throw new WrongParameter("Данный пользователь не является инициатором события.");
        }
        repository.increaseViews(eventId);
        return mapper.toDto(event);
    }

    @Override
    public Collection<ParticipationRequestDto> findRequests(int userId, int eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!event.getInitiator().equals(user)) {
            throw new WrongParameter("Данный пользователь не является инициатором события.");
        }
        return requestsRepository.findByEventId(eventId).stream().map(requestsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto update(UpdateEventUserRequest dto, int userId, int eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!event.getInitiator().equals(user)) {
            throw new WrongParameter("Данный пользователь не является инициатором события.");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new WrongParameter("Изменить можно только отмененные события или события в состоянии ожидания модерации.");
        }

        if (dto.getAnnotation() != null && !dto.getAnnotation().isBlank()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(dto.getCategory()).orElseThrow(() -> new CategoryNotFound(dto.getCategory())));
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getLocation() != null) {
            event.setLocation(locationMapper.fromDto(dto.getLocation()));
        }
        event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        event.setRequestModeration(dto.getRequestModeration());
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            event.setTitle(dto.getTitle());
        }
        if (StateAction.from(dto.getStateAction()).isPresent()) {
            if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            } else if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            } else {
                throw new WrongParameter("Указано недопустимое состояние.");
            }
        }

        return mapper.toDto(repository.save(event));
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest dto, int userId, int eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = repository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!event.getInitiator().equals(user)) {
            throw new WrongParameter("Данный пользователь не является инициатором события.");
        }
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            throw new NoConfirmationNeeded();
        }

        if (RequestStatus.valueOf(dto.getStatus()).equals(RequestStatus.REJECTED)) {
            return rejectRequest(dto.getRequestIds());
        }

        checkEventLimit(event);

        if (RequestStatus.valueOf(dto.getStatus()).equals(RequestStatus.CONFIRMED)) {
            return confirmRequest(event, dto.getRequestIds());
        }

        throw new WrongParameter("Указано недопустимое состояние.");
    }

    private EventRequestStatusUpdateResult confirmRequest(Event event, Collection<Integer> requestIds) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        int confirmedRequests = event.getConfirmedRequests();
        for (int requestId : requestIds) {
            if (event.getParticipantLimit() == confirmedRequests) {
                throw new WrongParameter("Достигнут лимит по заявкам.");
            }
            ParticipationRequest request = requestsRepository.findById(requestId)
                    .orElseThrow(() -> new ParticipationRequestNotFound(requestId));
            request.setStatus(RequestStatus.CONFIRMED);
            result.getConfirmedRequests().add(requestsMapper.toDto(request));
            repository.increaseConfirmedRequests(event.getId());
            confirmedRequests++;
        }
        return result;
    }

    private EventRequestStatusUpdateResult rejectRequest(Collection<Integer> requestIds) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        for (int requestId : requestIds) {
            ParticipationRequest request = requestsRepository.findById(requestId)
                    .orElseThrow(() -> new ParticipationRequestNotFound(requestId));
            checkRequestStatus(request);
            request.setStatus(RequestStatus.REJECTED);
            result.getRejectedRequests().add(requestsMapper.toDto(request));
        }
        return result;
    }

    private void checkRequestStatus(ParticipationRequest request) {
        if (request.getStatus().equals(RequestStatus.REJECTED) ||
                request.getStatus().equals(RequestStatus.CONFIRMED)) {
            throw new WrongParameter("Статус можно изменить только у заявок, находящихся в состоянии ожидания.");
        }
    }

    private void checkEventLimit(Event event) {
        if (event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new WrongParameter("Достигнут лимит по заявкам.");
        }
    }

}