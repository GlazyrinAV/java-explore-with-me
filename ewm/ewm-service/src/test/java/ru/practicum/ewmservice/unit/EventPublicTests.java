package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmclient.model.CategoryDto;
import ru.practicum.ewmclient.model.EventDto;
import ru.practicum.ewmclient.model.LocationDto;
import ru.practicum.ewmclient.model.UserDto;
import ru.practicum.ewmservice.exceptions.exceptions.BadParameter;
import ru.practicum.ewmservice.exceptions.exceptions.EventNotFound;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.service.event.EventPublicService;
import ru.practicum.ewmservice.service.event.EventPublicServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class EventPublicTests {

    private final EventRepository mockRepository = Mockito.mock(EventRepository.class);

    private final EventMapper mockMapper = Mockito.mock(EventMapper.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final EventPublicService service = new EventPublicServiceImpl(mockRepository, mockMapper, formatter);

    @Test
    void findAllNormalSortDate() {
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
        when(mockRepository.findAllPublicWithCriteriaSortDate(any(), anyString(), anyCollection(), anyBoolean(), anyString(), anyString(), anyBoolean()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(List.of(eventDto), service.findAll(0, 10, "test", List.of(1),
                true, "2011-11-11 11:11", "2012-12-12 12:12", true, "EVENT_DATE"));
        Mockito.verify(mockRepository, times(1))
                .findAllPublicWithCriteriaSortDate(any(), anyString(), anyCollection(), anyBoolean(), anyString(), anyString(), anyBoolean());
    }

    @Test
    void findAllNormalSortViews() {
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
        when(mockRepository.findAllPublicWithCriteria(any(), anyString(), anyCollection(), anyBoolean(), anyString(), anyString(), anyBoolean()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(List.of(eventDto), service.findAll(0, 10, "test", List.of(1),
                true, "2011-11-11 11:11", "2012-12-12 12:12", true, "VIEWS"));
        Mockito.verify(mockRepository, times(1))
                .findAllPublicWithCriteria(any(), anyString(), anyCollection(), anyBoolean(), anyString(), anyString(), anyBoolean());
    }

    @Test
    void findAllNormalSortMarks() {
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
        when(mockRepository.findAllPublicWithCriteriaSortMark(any(), anyString(), anyCollection(), anyBoolean(), anyString(), anyString(), anyBoolean()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(List.of(eventDto), service.findAll(0, 10, "test", List.of(1),
                true, "2011-11-11 11:11", "2012-12-12 12:12", true, "WRONG"));
        Mockito.verify(mockRepository, times(1))
                .findAllPublicWithCriteriaSortMark(any(), anyString(), anyCollection(), anyBoolean(), anyString(), anyString(), anyBoolean());
    }

    @Test
    void findAllWrongPeriod() {
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.findAll(0, 10, "test", List.of(1),
                true, "2012-12-12 12:12", "2011-11-11 11:11", true, "WRONG"));

        Assertions.assertEquals("Дата начала не может быть позже даты конца.", exception.getMessage());
    }

    @Test
    void findById() {
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
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(eventDto, service.findById(1));
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    void findByIdWrongState() {
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
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.of(2011, 10, 11, 10, 10))
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(event));
        EventNotFound exception = Assertions.assertThrows(EventNotFound.class, () -> service.findById(1));

        Assertions.assertEquals("Событие должно быть опубликовано.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

}