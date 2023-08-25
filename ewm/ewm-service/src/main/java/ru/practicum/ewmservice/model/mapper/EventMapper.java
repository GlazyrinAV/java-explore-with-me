package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmcommondto.exceptions.CategoryNotFound;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmcommondto.model.NewEventDto;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.LocationRepository;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;

    private final LocationMapper locationMapper;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

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
            location = locationRepository.save(locationMapper.fromDto(dto.getLocation()));
        }
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(categoryRepository.findById(dto.getCategory()).orElseThrow(() -> new CategoryNotFound(dto.getCategory())))
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
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
                .eventDate(dto.getEventDate())
                .paid(dto.isPaid())
                .location(locationMapper.fromDto(dto.getLocation()))
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .build();
    }

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(userMapper.toDto(event.getInitiator()))
                .location(locationMapper.toDto(event.getLocation()))
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.isRequestModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public EventDto toShortDto(Event event) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(userMapper.toDto(event.getInitiator()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

}