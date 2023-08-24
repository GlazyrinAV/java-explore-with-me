package ru.practicum.ewmservice.service.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.exceptions.ParticipationRequestNotFound;
import ru.practicum.ewmcommondto.exceptions.UserNotFound;
import ru.practicum.ewmcommondto.model.ParticipationRequestDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.RequestStatus;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationRequestsPrivateServiceImpl implements ParticipationRequestsPrivateService {

    private final ParticipationRequestsRepository repository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final ParticipationRequestsMapper mapper;

    @Override
    public ParticipationRequestDto save(int usersId, int eventId) {
        User user = userRepository.findById(usersId).orElseThrow(() -> new UserNotFound(usersId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        ParticipationRequest request = ParticipationRequest.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .status(RequestStatus.PENDING)
                .build();
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
        request.setStatus(RequestStatus.REJECTED);
        return mapper.toDto(repository.save(request));
    }

}