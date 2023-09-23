package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmclient.model.*;
import ru.practicum.ewmservice.exceptions.exceptions.*;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.*;
import ru.practicum.ewmservice.service.event.EventPrivateService;
import ru.practicum.ewmservice.service.event.EventPrivateServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class EventPrivateTests {

    private final EventRepository mockRepository = Mockito.mock(EventRepository.class);

    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);

    private final ParticipationRequestsRepository mockRequestsRepository = Mockito.mock(ParticipationRequestsRepository.class);

    private final CategoryRepository mockCategoryRepository = Mockito.mock(CategoryRepository.class);

    private final LocationRepository mockLocationRepository = Mockito.mock(LocationRepository.class);

    private final EventMapper mockMapper = Mockito.mock(EventMapper.class);

    private final LocationMapper mockLocationMapper = Mockito.mock(LocationMapper.class);

    private final ParticipationRequestsMapper mockRequestsMapper = Mockito.mock(ParticipationRequestsMapper.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final EventPrivateService service = new EventPrivateServiceImpl(mockRepository, mockUserRepository,
            mockRequestsRepository, mockCategoryRepository, mockLocationRepository, mockMapper,
            mockLocationMapper, mockRequestsMapper, formatter);

    @Test
    void saveNormal() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        NewEventDto newEventDto = NewEventDto.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockMapper.fromDto(newEventDto))
                .thenReturn(event);
        when(mockRepository.save(any()))
                .thenReturn(event);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(eventDto, service.save(newEventDto, 1));
        Mockito.verify(mockRepository, times(1)).save(event);
    }

    @Test
    void saveWrongUserId() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        NewEventDto newEventDto = NewEventDto.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.save(newEventDto, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveWrongEventDate() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        NewEventDto newEventDto = NewEventDto.builder()
                .eventDate("2020-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2020, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockMapper.fromDto(newEventDto))
                .thenReturn(event);
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new User()));
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.save(newEventDto, 1));

        Assertions.assertEquals("Начало события не ранее чем за 2 часа.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void findAllByUserIdNormal() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2011, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2011-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        Page<Event> page = new PageImpl<>(List.of(event));
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findByInitiatorId(anyInt(), any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(List.of(eventDto), service.findAllByUserId(1, 0, 10));
        Mockito.verify(mockRepository, times(1)).findByInitiatorId(anyInt(), any());
    }

    @Test
    void findAllByUserIdWrongUserId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.findAllByUserId(1, 0, 10));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).findByInitiatorId(anyInt(), any());
    }

    @Test
    void findByIdNormal() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2011, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2011-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PUBLISHED")
                .publishedOn("2011-10-11 11:11")
                .views(10)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(eventDto, service.findById(1, 1));
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    void findByIdWrongUserId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.findById(1, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).findById(anyInt());
    }

    @Test
    void findByIdWrongEventId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new User()));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.findById(1, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(anyInt());
    }

    @Test
    void findByIdNotByOwner() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(1)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2011, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.findById(1, 1));

        Assertions.assertEquals("Данный пользователь не является инициатором события.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    void findRequestsNormal() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        ParticipationRequest request = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.PENDING)
                .id(1)
                .build();
        ParticipationRequestDto dto = ParticipationRequestDto.builder()
                .id(1)
                .requester(1)
                .event(1)
                .status("PENDING")
                .created("2011-11-11 11:11")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findByEventId(anyInt()))
                .thenReturn(List.of(request));
        when(mockRequestsMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(List.of(dto), service.findRequests(1, 1));
        Mockito.verify(mockRequestsRepository, times(1)).findByEventId(1);
    }

    @Test
    void findRequestsWrongUserId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.findRequests(1, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void findRequestsWrongEventId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new User()));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.findRequests(1, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void findRequestsWrongOwner() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.findRequests(1, 1));

        Assertions.assertEquals("Данный пользователь не является инициатором события.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void updateNormal() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        EventDto eventDto = EventDto.builder()
                .id(1)
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .category(categoryDto)
                .confirmedRequests(10)
                .createdOn("2011-10-10 10:10")
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(userDto)
                .location(locationDto)
                .requestModeration(true)
                .state("PENDING")
                .views(10)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        when(mockRepository.save(any()))
                .thenReturn(event);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(eventDto, service.update(newEventDto, 1, 1));
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void updateWrongUserId() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.update(newEventDto, 1, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void updateWrongEventId() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new User()));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.update(newEventDto, 1, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void updateWrongEventDate() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2022, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.update(newEventDto, 1, 1));


        Assertions.assertEquals("До даты события должно быть не менее чем 2 часа.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void updateWrongOwner() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new User()));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.update(newEventDto, 1, 1));


        Assertions.assertEquals("Данный пользователь не является инициатором события.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void updateWrongEventState() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.update(newEventDto, 1, 1));


        Assertions.assertEquals("Изменить можно только отмененные события или события в состоянии ожидания модерации.",
                exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void updateWrongEventNewDate() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventUserRequest newEventDto = UpdateEventUserRequest.builder()
                .eventDate("2022-11-11 11:11")
                .annotation("testAnnotation")
                .title("testTitle")
                .paid(true)
                .requestModeration(true)
                .participantLimit(10)
                .category(1)
                .description("testDescription")
                .location(locationDto)
                .stateAction("SEND_TO_REVIEW")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .initiator(user)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.update(newEventDto, 1, 1));


        Assertions.assertEquals("До новой даты события должно быть не менее чем 2 часа.",
                exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void updateRequestsNormalConfirmed() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        ParticipationRequest requestBefore = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.PENDING)
                .id(1)
                .build();
        ParticipationRequest requestAfter = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.CONFIRMED)
                .id(1)
                .build();
        ParticipationRequestDto approved = ParticipationRequestDto.builder()
                .status("CONFIRMED")
                .event(1)
                .requester(1)
                .build();
        EventRequestStatusUpdateResult dto = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(List.of(approved))
                .rejectedRequests(List.of())
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findById(1))
                .thenReturn(Optional.ofNullable(requestBefore));
        when(mockRequestsRepository.findConfirmedRequests(anyInt()))
                .thenReturn(0);
        when(mockRequestsRepository.save(any()))
                .thenReturn(requestAfter);
        when(mockRequestsMapper.toDto(any()))
                .thenReturn(approved);

        EventRequestStatusUpdateResult result = service.updateRequests(updateRequest, 2, 1);

        Assertions.assertEquals(dto.getConfirmedRequests(), result.getConfirmedRequests());
        Assertions.assertEquals(dto.getRejectedRequests(), result.getRejectedRequests());
    }

    @Test
    void updateRequestsNormalReject() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        ParticipationRequest requestBefore = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.PENDING)
                .id(1)
                .build();
        ParticipationRequest requestAfter = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.CONFIRMED)
                .id(1)
                .build();
        ParticipationRequestDto rejected = ParticipationRequestDto.builder()
                .status("REJECTED")
                .event(1)
                .requester(1)
                .build();
        EventRequestStatusUpdateResult dto = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(List.of())
                .rejectedRequests(List.of(rejected))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("REJECTED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findById(1))
                .thenReturn(Optional.ofNullable(requestBefore));
        when(mockRequestsRepository.findConfirmedRequests(anyInt()))
                .thenReturn(2);
        when(mockRequestsRepository.save(any()))
                .thenReturn(requestAfter);
        when(mockRequestsMapper.toDto(any()))
                .thenReturn(rejected);

        EventRequestStatusUpdateResult result = service.updateRequests(updateRequest, 2, 1);

        Assertions.assertEquals(dto.getRejectedRequests(), result.getRejectedRequests());
        Assertions.assertEquals(dto.getConfirmedRequests(), result.getConfirmedRequests());
    }

    @Test
    void updateRequestsNormalWithLimit() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        ParticipationRequest requestBefore = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.PENDING)
                .id(1)
                .build();
        ParticipationRequest requestBefore2 = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.PENDING)
                .id(2)
                .build();
        ParticipationRequest requestAfter = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.of(2011, 11, 11, 11, 11))
                .status(RequestStatus.CONFIRMED)
                .id(1)
                .build();
        ParticipationRequestDto approved = ParticipationRequestDto.builder()
                .status("CONFIRMED")
                .event(1)
                .requester(1)
                .build();
        ParticipationRequestDto rejected = ParticipationRequestDto.builder()
                .status("CONFIRMED")
                .event(1)
                .requester(1)
                .build();
        EventRequestStatusUpdateResult dto = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(List.of(approved))
                .rejectedRequests(List.of(rejected))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1, 2))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findById(1))
                .thenReturn(Optional.ofNullable(requestBefore));
        when(mockRequestsRepository.findById(2))
                .thenReturn(Optional.ofNullable(requestBefore2));
        when(mockRequestsRepository.findConfirmedRequests(anyInt()))
                .thenReturn(1);
        when(mockRequestsRepository.save(any()))
                .thenReturn(requestAfter);
        when(mockRequestsMapper.toDto(any()))
                .thenReturn(approved);

        EventRequestStatusUpdateResult result = service.updateRequests(updateRequest, 2, 1);

        Assertions.assertEquals(dto.getRejectedRequests(), result.getRejectedRequests());
        Assertions.assertEquals(dto.getConfirmedRequests(), result.getConfirmedRequests());
    }

    @Test
    void updateRequestsWrongUserId() {
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Пользователь с ID 2 не найден.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void updateRequestsWrongEventId() {
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new User()));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRequestsRepository, times(0)).findByEventId(1);
    }

    @Test
    void updateRequestsFullLimit() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findConfirmedRequests(anyInt()))
                .thenReturn(2);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () ->
                service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Достигнут лимит по заявкам.", exception.getMessage());
    }

    @Test
    void updateRequestsWrongOwner() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () ->
                service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Данный пользователь не является инициатором события.", exception.getMessage());
    }

    @Test
    void updateRequestsWithoutConfirm() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(false)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        NoConfirmationNeeded exception = Assertions.assertThrows(NoConfirmationNeeded.class, () ->
                service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Для данного события не требуется подтверждение участия.", exception.getMessage());
    }

    @Test
    void updateRequestsLimitZero() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(0)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("CONFIRMED")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        NoConfirmationNeeded exception = Assertions.assertThrows(NoConfirmationNeeded.class, () ->
                service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Для данного события не требуется подтверждение участия.", exception.getMessage());
    }

    @Test
    void updateRequestsWrongAction() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("testUser2")
                .email("test2@email.ru")
                .build();
        Location location = Location.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        Event event = Event.builder()
                .id(1)
                .eventDate(LocalDateTime.of(2024, 11, 11, 11, 11))
                .annotation("testAnnotation")
                .category(category)
                .createdOn(LocalDateTime.of(2011, 10, 10, 10, 10))
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(2)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        EventRequestStatusUpdateRequest updateRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1))
                .status("WRONG")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () ->
                service.updateRequests(updateRequest, 2, 1));

        Assertions.assertEquals("Указано недопустимое состояние.", exception.getMessage());
    }

}