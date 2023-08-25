package ru.practicum.ewmservice.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.CategoryNotFound;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.exceptions.WrongParameter;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmcommondto.model.UpdateEventAdminRequest;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.model.StateAction;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.LocationRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository repository;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final EventMapper mapper;

    private final LocationMapper locationMapper;

    @Override
    public Collection<EventDto> findAll(int from,
                                        int size,
                                        Integer[] users,
                                        String[] states,
                                        Integer[] categories,
                                        String rangeStart,
                                        String rangeEnd) {
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);
        return repository.findAllAdminWithCriteria(page, users, states, categories, rangeStart, rangeEnd)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto update(UpdateEventAdminRequest dto, int id) {
        Event event = repository.findById(id).orElseThrow(() -> new EventNotFound(id));
        Optional<StateAction> stateAction = StateAction.from(dto.getStateAction());
        if (stateAction.isEmpty()) {
            throw new WrongParameter("Неправильно указан параметр состояния.");
        }

        if (!dto.getAnnotation().isBlank()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(dto.getCategory()).orElseThrow(() -> new CategoryNotFound(dto.getCategory())));
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
        if (dto.getLocation() != null) {
            Location location = locationRepository.findByLatAndLon(dto.getLocation().getLat(), dto.getLocation().getLon());
            if (location == null) {
                location = locationRepository.save(locationMapper.fromDto(dto.getLocation()));
            }
            event.setLocation(location);
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (!dto.getTitle().isBlank()) {
            event.setTitle(dto.getTitle());
        }

        if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.PUBLISH_EVENT)) {
            if (!event.getState().equals(EventState.PENDING)) {
                throw new WrongParameter("Событие можно публиковать, только если оно в состоянии ожидания публикации.");
            }
            event.setState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        } else if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.REJECT_EVENT)) {
            if (event.getState().equals(EventState.PUBLISHED)) {
                throw new WrongParameter("Событие можно отклонить, только если оно еще не опубликовано.");
            }
            event.setState(EventState.CANCELED);
        } else {
            throw new WrongParameter("Указано недопустимое состояние.");
        }

        return mapper.toDto(repository.save(event));
    }

}