package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.exceptions.WrongParameter;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.SortType;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepository repository;

    private final EventMapper mapper;

    @Override
    public Collection<EventDto> findAll(int from,
                                        int size,
                                        String text,
                                        Integer[] categories,
                                        Boolean paid,
                                        String rangeStart,
                                        String rangeEnd,
                                        Boolean onlyAvailable,
                                        String sort) {
        Pageable page=  PageRequest.of(from == 0 ? 0 : from / size, size);
        if (SortType.from(sort).isPresent()) {
            page = PageRequest.of(from == 0 ? 0 : from / size, size, Sort.by(sort));
        }
        return repository.findAllPublicWithCriteria(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                .stream().map(mapper::toShortDto).collect(Collectors.toList());
    }

    public EventDto findById(int id) {
        Event event = repository.findById(id).orElseThrow(() -> new EventNotFound(id));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new WrongParameter("Событие должно быть опубликовано.");
        }
        return mapper.toDto(event);
    }

}