package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.*;
import ru.practicum.ewmservice.service.event.EventPrivateService;
import ru.practicum.ewmservice.service.event.EventPrivateServiceImpl;

import java.time.format.DateTimeFormatter;

class EventPrivateTests {

    private final EventRepository mockRepository = Mockito.mock(EventRepository.class);

    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);

    private final ParticipationRequestsRepository mockRequestsRepository = Mockito.mock(ParticipationRequestsRepository.class);

    private final CategoryRepository mockCategoryRepository = Mockito.mock(CategoryRepository.class);

    private final LocationRepository mockLocationRepository = Mockito.mock(LocationRepository.class);

    private final EventMapper mockMapper = Mockito.mock(EventMapper.class);

    private final LocationMapper mockLocationMapper = Mockito.mock(LocationMapper.class);

    private final ParticipationRequestsMapper mockRequestsMapper = Mockito.mock(ParticipationRequestsMapper.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventPrivateService service = new EventPrivateServiceImpl(mockRepository, mockUserRepository,
            mockRequestsRepository, mockCategoryRepository, mockLocationRepository, mockMapper,
            mockLocationMapper, mockRequestsMapper, formatter);

    @Test
    void save() {

    }

    @Test
    void findAllByUserId() {

    }

    @Test
    void findById() {

    }

    @Test
    void findRequests() {

    }

    @Test
    void update() {

    }

    @Test
    void updateRequests() {

    }

}