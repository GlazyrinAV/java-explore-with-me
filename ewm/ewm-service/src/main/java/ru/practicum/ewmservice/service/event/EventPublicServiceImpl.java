package ru.practicum.ewmservice.service.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exceptions.exceptions.BadParameter;
import ru.practicum.ewmservice.exceptions.exceptions.EventNotFound;
import ru.practicum.ewmclient.model.EventDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.SortType;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.repository.EventRepository;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
        if (rangeStart != null && rangeEnd != null) {
            if (LocalDateTime.parse(rangeStart, formatter).isAfter(LocalDateTime.parse(rangeEnd, formatter))) {
                throw new BadParameter("Дата начала не может быть позже даты конца.");
            }
        }
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);;

        if (SortType.from(sort).isPresent()) {
            if (sort.equals(SortType.EVENT_DATE.name())) {
                List<EventDto> result = repository.findAllPublicWithCriteria(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                        .stream()
                        .map(mapper::toDto)
                        .sorted(Comparator.comparing(EventDto::getEventDate))
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(EventDto::getEventDate).reversed());
                return  result;
               } else {
                List<EventDto> result = repository.findAllPublicWithCriteria(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                        .stream()
                        .map(mapper::toDto)
                        .sorted(Comparator.comparing(EventDto::getViews).reversed())
                        .collect(Collectors.toList());
                result.sort(Comparator.comparing(EventDto::getViews).reversed());
                return result;
                }
        }
        List<EventDto> result = repository.findAllPublicWithCriteria(page, text, categories, paid, rangeStart, rangeEnd, onlyAvailable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        result.sort(new EventComparatorByMarks().reversed());
        return result;
    }

    public EventDto findById(int id) {
        Event event = repository.findById(id).orElseThrow(() -> new EventNotFound(id));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new EventNotFound("Событие должно быть опубликовано.");
        }
        return mapper.toDto(event);
    }

    static class EventComparatorByMarks implements Comparator<EventDto> {

        @Override
        public int compare(EventDto o1, EventDto o2) {
            BigDecimal markO1 = o1.getMark();
            BigDecimal mark02 = o2.getMark();

            if (markO1 == null && mark02 == null) {
                return 0;
            } else if (markO1 == null) {
                return -1;
            } else if (mark02 == null) {
                return 1;
            }
            return markO1.subtract(mark02).toBigInteger().intValue();
        }
    }

}