package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.ewmclient.model.mark.MarkDto;
import ru.practicum.ewmservice.exceptions.exceptions.*;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.MarkMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.MarkRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;
import ru.practicum.ewmservice.service.marks.MarkService;
import ru.practicum.ewmservice.service.marks.MarkServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class MarksTests {

    private final MarkRepository mockRepository = Mockito.mock(MarkRepository.class);

    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);

    private final EventRepository mockEventRepository = Mockito.mock(EventRepository.class);

    private final ParticipationRequestsRepository mockRequestsRepository = Mockito.mock(ParticipationRequestsRepository.class);

    private final MarkMapper mockMapper = Mockito.mock(MarkMapper.class);

    private final MarkService service = new MarkServiceImpl(mockRepository, mockUserRepository, mockEventRepository, mockRequestsRepository, mockMapper);

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
                .created(LocalDateTime.now())
                .id(1)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        Mark eventMark = Mark.builder()
                .user(user1)
                .event(event)
                .mark(5)
                .markId(new MarkId(1, 1))
                .build();
        MarkDto dto = MarkDto.builder()
                .eventId(1)
                .userId(1)
                .mark(5)
                .build();
        when(mockRepository.save(eventMark))
                .thenReturn(eventMark);
        when(mockMapper.toDto(any()))
                .thenReturn(dto);
        when(mockRequestsRepository.findRequestByUserForMarks(anyInt(), anyInt()))
                .thenReturn(request);
        when(mockRepository.findByUser_IdAndEvent_Id(anyInt(), anyInt()))
                .thenReturn(null);

        Assertions.assertEquals(dto, service.save(1, 1, 5));
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void saveWrongUserId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.save(1, 1, 1));

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
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.save(1, 1, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveWrongMark0() {
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
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.save(1, 1, 0));

        Assertions.assertEquals("Оценка должна быть в пределах от 1 до 5.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveWrongMark6() {
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
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.save(1, 1, 6));

        Assertions.assertEquals("Оценка должна быть в пределах от 1 до 5.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveWrongEventState() {
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
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1, 1));

        Assertions.assertEquals("Оценивать можно только опубликованные события.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveNoParticipationRequest() {
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
        when(mockRequestsRepository.findRequestByUserForMarks(anyInt(), anyInt()))
                .thenReturn(null);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1, 1));

        Assertions.assertEquals("Оценку может ставить пользователь принимавший участие в событии.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveMarkAlreadySet() {
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
                .created(LocalDateTime.now())
                .id(1)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findRequestByUserForMarks(anyInt(), anyInt()))
                .thenReturn(request);
        when(mockRepository.findByUser_IdAndEvent_Id(anyInt(), anyInt()))
                .thenReturn(new Mark());
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1, 1));

        Assertions.assertEquals("Оценка уже поставлена.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void saveMarkByAuthor() {
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
        ParticipationRequest request = ParticipationRequest.builder()
                .requester(user1)
                .event(event)
                .created(LocalDateTime.now())
                .id(1)
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRequestsRepository.findRequestByUserForMarks(anyInt(), anyInt()))
                .thenReturn(request);
        when(mockRepository.findByUser_IdAndEvent_Id(anyInt(), anyInt()))
                .thenReturn(null);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(1, 1, 1));

        Assertions.assertEquals("Автор мероприятия не может ставить оценку.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void removeNormal() {
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
        Mark eventMark = Mark.builder()
                .user(user1)
                .event(event)
                .mark(5)
                .markId(new MarkId(1, 1))
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockRepository.findByUser_IdAndEvent_Id(anyInt(), anyInt()))
                .thenReturn(eventMark);
        service.remove(1, 1);

        Mockito.verify(mockRepository, times(1)).delete(eventMark);
    }

    @Test
    void removeWrongUserId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.remove(1, 1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).delete(any());
    }

    @Test
    void removeWrongEventId() {
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.remove(1, 1));

        Assertions.assertEquals("Событие с ID 1 не найдено.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).delete(any());
    }

    @Test
    void removeMurkNull() {
        User user1 = User.builder()
                .id(1)
                .name("testUser")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(user1));
        when(mockEventRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Event()));
        when(mockRepository.findByUser_IdAndEvent_Id(anyInt(), anyInt()))
                .thenReturn(null);
        MarkNotFound exception = Assertions.assertThrows(MarkNotFound.class, () -> service.remove(1, 1));

        Assertions.assertEquals("Оценка не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).delete(any());
    }

}