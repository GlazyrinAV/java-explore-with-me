package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.model.event.EventDto;
import ru.practicum.ewmservice.exceptions.exceptions.BadParameter;
import ru.practicum.ewmservice.exceptions.exceptions.EventNotFound;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.SortType;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.repository.EventRepository;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepository repository;

    private final EventMapper mapper;

    private final DateTimeFormatter formatter;

    @Override
    public Collection<EventDto> findAll(int from,
                                        int size,
                                        String text,
                                        Collection<Integer> categories,
                                        Boolean paid,
                                        String rangeStart,
                                        String rangeEnd,
                                        Boolean onlyAvailable,
                                        String sort) {
        if (rangeStart != null) {
            rangeStart = URLDecoder.decode(rangeStart, Charset.defaultCharset());
        }
        if (rangeEnd != null) {
            rangeEnd = URLDecoder.decode(rangeEnd, Charset.defaultCharset());
        }
        if (rangeStart != null && rangeEnd != null && LocalDateTime.parse(rangeStart, formatter)
                .isAfter(LocalDateTime.parse(rangeEnd, formatter))) {
            throw new BadParameter("Дата начала не может быть позже даты конца.");
        }
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);

        if (SortType.from(sort).isPresent()) {
            if (sort.equals(SortType.EVENT_DATE.name())) {
                return repository.findAllPublicWithCriteriaSortDate(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                        .stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList());
            } else {
                return repository.findAllPublicWithCriteria(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                        .stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList());
            }
        }
        return repository.findAllPublicWithCriteriaSortMark(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public EventDto findById(int id) {
        Event event = repository.findById(id).orElseThrow(() -> new EventNotFound(id));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new EventNotFound("Событие должно быть опубликовано.");
        }
        return mapper.toDto(event);
    }

}