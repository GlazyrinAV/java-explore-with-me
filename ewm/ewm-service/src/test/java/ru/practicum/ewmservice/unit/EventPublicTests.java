package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.service.event.EventPublicService;
import ru.practicum.ewmservice.service.event.EventPublicServiceImpl;

import java.time.format.DateTimeFormatter;

class EventPublicTests {

    private final EventRepository mockRepository = Mockito.mock(EventRepository.class);

    private final EventMapper mockMapper = Mockito.mock(EventMapper.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventPublicService service = new EventPublicServiceImpl(mockRepository, mockMapper, formatter);

    @Test
    void findAll() {

    }

    @Test
    void findById() {

    }

}