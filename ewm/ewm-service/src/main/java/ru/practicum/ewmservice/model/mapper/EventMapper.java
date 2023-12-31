package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.exceptions.exceptions.CategoryNotFound;
import ru.practicum.ewmclient.model.EventDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmclient.model.NewEventDto;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.LocationRepository;
import ru.practicum.ewmservice.repository.MarkRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;

    private final LocationMapper locationMapper;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final ParticipationRequestsRepository requestsRepository;

    private final MarkRepository markRepository;

    private final DateTimeFormatter formatter;

    public Event fromDto(NewEventDto dto) {
        boolean paidDto = false;
        if (dto.getPaid() != null) {
            paidDto = dto.getPaid();
        }
        boolean moderation = true;
        if (dto.getRequestModeration() != null) {
            moderation = dto.getRequestModeration();
        }
        Location location = locationRepository.findByLatAndLon(dto.getLocation().getLat(), dto.getLocation().getLon());
        if (location == null) {
            location = locationRepository.saveAndFlush(locationMapper.fromDto(dto.getLocation()));
        }
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(categoryRepository.findById(dto.getCategory()).orElseThrow(() -> new CategoryNotFound(dto.getCategory())))
                .description(dto.getDescription())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), formatter))
                .paid(paidDto)
                .location(location)
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(moderation)
                .title(dto.getTitle())
                .build();
    }

    public Event fromDto(EventDto dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(categoryMapper.fromDto(dto.getCategory()))
                .description(dto.getDescription())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), formatter))
                .paid(dto.isPaid())
                .location(locationMapper.fromDto(dto.getLocation()))
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .build();
    }

    public EventDto toDto(Event event) {
        String published = null;
        if (event.getPublishedOn() != null) {
            published = event.getPublishedOn().format(formatter);
        }
        EventDto dto = EventDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(requestsRepository.findConfirmedRequests(event.getId()))
                .createdOn(event.getCreatedOn().format(formatter))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(formatter))
                .id(event.getId())
                .initiator(userMapper.toDto(event.getInitiator()))
                .location(locationMapper.toDto(event.getLocation()))
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(published)
                .requestModeration(event.isRequestModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

        Double mark = markRepository.findMarkForEvent(event.getId());
        if (mark != null) {
            dto.setMark(BigDecimal.valueOf(mark).setScale(1, RoundingMode.UP));
        }

        return dto;
    }

    public EventDto toShortDto(Event event) {
        EventDto dto = EventDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(requestsRepository.findConfirmedRequests(event.getId()))
                .eventDate(event.getEventDate().format(formatter))
                .id(event.getId())
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

        Double mark = markRepository.findMarkForEvent(event.getId());
        if (mark != null) {
            dto.setMark(BigDecimal.valueOf(mark).setScale(1, RoundingMode.UP));
        }

        return dto;
    }

}