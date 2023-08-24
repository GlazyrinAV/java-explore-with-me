package ru.practicum.ewmservice.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.exceptions.WrongParametr;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.StateAction;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository repository;

    private final EventMapper mapper;

    private final CategoryMapper categoryMapper;

    private final LocationMapper locationMapper;

    @Override
    public Collection<EventDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto update(EventDto dto, int id) {
        Event event = repository.findById(id).orElseThrow(() -> new EventNotFound(id));
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
            if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
            } else if (StateAction.valueOf(dto.getStateAction()).equals(StateAction.REJECT_EVENT)){
                event.setState(EventState.CANCELED);
            } else {
                throw new WrongParametr("Указано недопустимое состояние.");
            }
        }
        return mapper.toDto(repository.save(event));
    }

}