package ru.practicum.ewmservice.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exceptions.exceptions.BadParameter;
import ru.practicum.ewmservice.exceptions.exceptions.CategoryNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.EventNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmclient.model.EventDto;
import ru.practicum.ewmclient.model.UpdateEventAdminRequest;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.model.StateAction;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.LocationRepository;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository repository;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final EventMapper mapper;

    private final LocationMapper locationMapper;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Collection<EventDto> findAll(int from,
                                        int size,
                                        Collection<Integer> users,
                                        Collection<String> states,
                                        Collection<Integer> categories,
                                        String rangeStart,
                                        String rangeEnd) {
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);
        if (rangeStart != null) {
            rangeStart = URLDecoder.decode(rangeStart, Charset.defaultCharset());
        }
        if (rangeEnd != null) {
            rangeEnd = URLDecoder.decode(rangeEnd, Charset.defaultCharset());
        }
        if (rangeStart != null && rangeEnd != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (LocalDateTime.parse(rangeStart, formatter).isAfter(LocalDateTime.parse(rangeEnd, formatter))) {
                throw new BadParameter("Дата начала не может быть позже даты конца.");
            }
        }
        return repository.findAllAdminWithCriteria(page, users, states, categories, rangeStart, rangeEnd)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto update(UpdateEventAdminRequest dto, int id) {
        Event event = repository.findById(id).orElseThrow(() -> new EventNotFound(id));
        if (!event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new WrongParameter("До даты события должно быть не менее чем 2 часа.");
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
            if (!LocalDateTime.parse(dto.getEventDate(), formatter).isAfter(LocalDateTime.now().plusHours(2))) {
                throw new BadParameter("До новой даты события должно быть не менее чем 2 часа.");
            }
            event.setEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
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
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            event.setTitle(dto.getTitle());
        }

        if (dto.getStateAction() != null) {
            Optional<StateAction> stateAction = StateAction.from(dto.getStateAction());
            if (stateAction.isEmpty()) {
                throw new WrongParameter("Неправильно указан параметр состояния.");
            }
            if (stateAction.get().equals(StateAction.PUBLISH_EVENT)) {
                if (!event.getState().equals(EventState.PENDING)) {
                    throw new WrongParameter("Событие можно публиковать, только если оно в состоянии ожидания публикации.");
                }
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (stateAction.get().equals(StateAction.REJECT_EVENT)) {
                if (event.getState().equals(EventState.PUBLISHED)) {
                    throw new WrongParameter("Событие можно отклонить, только если оно еще не опубликовано.");
                }
                event.setState(EventState.CANCELED);
            } else {
                throw new WrongParameter("Указано недопустимое состояние.");
            }
        }

        return mapper.toDto(repository.save(event));
    }

}