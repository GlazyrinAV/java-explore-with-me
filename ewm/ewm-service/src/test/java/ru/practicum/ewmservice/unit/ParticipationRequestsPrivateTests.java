package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.ewmclient.model.ParticipationRequestDto;
import ru.practicum.ewmservice.exceptions.exceptions.EventNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.ParticipationRequestNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.UserNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.ParticipationRequestsMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;
import ru.practicum.ewmservice.service.requests.ParticipationRequestsPrivateService;
import ru.practicum.ewmservice.service.requests.ParticipationRequestsPrivateServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ParticipationRequestsPrivateTests {

    private final ParticipationRequestsRepository mockRepository = Mockito.mock(ParticipationRequestsRepository.class);

    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);

    private final EventRepository mockEventRepository = Mockito.mock(EventRepository.class);

    private final ParticipationRequestsMapper mockMapper = Mockito.mock(ParticipationRequestsMapper.class);

    private final ParticipationRequestsPrivateService service = new ParticipationRequestsPrivateServiceImpl(mockRepository, mockUserRepository, mockEventRepository, mockMapper);

    @Test
    void save() {
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
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRepository.existsByRequesterIdAndEventId(anyInt(), anyInt()))
                .thenReturn(false);
        when(mockRepository.findConfirmedRequests(anyInt()))
                .thenReturn(0);
        when(mockRepository.save(any()))
                .thenReturn(request);
        when(mockMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(dto, service.save(1, 1));
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void saveWrongUserId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.save(1, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveWrongEventId() {
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.save(1, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveEventNotPublished() {
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
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1));

        Assertions.assertEquals("Нельзя участвовать в неопубликованном событии.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveByAuthor() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        User user1 = User.builder()
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
                .initiator(user1)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1));

        Assertions.assertEquals("Инициатор события не может добавить запрос на участие в своём событии.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveSecondRequest() {
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
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRepository.existsByRequesterIdAndEventId(anyInt(), anyInt()))
                .thenReturn(true);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1));

        Assertions.assertEquals("Нельзя добавить повторный запрос.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveFull() {
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
                .participantLimit(1)
                .initiator(user2)
                .location(location)
                .requestModeration(true)
                .state(EventState.PUBLISHED)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRepository.existsByRequesterIdAndEventId(anyInt(), anyInt()))
                .thenReturn(false);
        when(mockRepository.findConfirmedRequests(anyInt()))
                .thenReturn(1);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1));

        Assertions.assertEquals("У события достигнут лимит запросов на участие.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void cancelNormal() {
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
                .status(RequestStatus.CANCELED)
                .id(1)
                .build();
        ParticipationRequestDto dto = ParticipationRequestDto.builder()
                .id(1)
                .requester(1)
                .event(1)
                .status("CANCELED")
                .created("2011-11-11 11:11")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(request));
        when(mockRepository.save(any()))
                .thenReturn(request);
        when(mockMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(dto, service.cancel(1, 1));
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void cancelWrongUser() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.cancel(1, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void cancelWrongRequest() {
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        ParticipationRequestNotFound exception = Assertions.assertThrows(ParticipationRequestNotFound.class, () -> service.cancel(1, 1));

        Assertions.assertEquals("Заявка с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void cancelWrongOwner() {
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
                .status(RequestStatus.CANCELED)
                .id(1)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user2));
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(request));
        when(mockRepository.save(any()))
                .thenReturn(request);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.cancel(2, 1));

        Assertions.assertEquals("Данный пользователь не создавал данный запрос.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void findAllNormal() {
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
                .thenReturn(Optional.ofNullable(user1));
        when(mockRepository.findByRequesterId(anyInt()))
                .thenReturn(List.of(request));
        when(mockMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(List.of(dto), service.findAll(1));
        Mockito.verify(mockRepository, times(1)).findByRequesterId(anyInt());
    }

    @Test
    void findAllWrongUser() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.findAll(1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).findById(any());
    }

}