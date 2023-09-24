package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmclient.model.*;
import ru.practicum.ewmservice.exceptions.exceptions.BadParameter;
import ru.practicum.ewmservice.exceptions.exceptions.CategoryNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.EventMapper;
import ru.practicum.ewmservice.model.mapper.LocationMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.LocationRepository;
import ru.practicum.ewmservice.service.event.EventAdminService;
import ru.practicum.ewmservice.service.event.EventAdminServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class EventAdminTests {

    private final EventRepository mockRepository = Mockito.mock(EventRepository.class);

    private final CategoryRepository mockCategoryRepository = Mockito.mock(CategoryRepository.class);

    private final LocationRepository mockLocationRepository = Mockito.mock(LocationRepository.class);

    private final EventMapper mockMapper = Mockito.mock(EventMapper.class);

    private final LocationMapper mockLocationMapper = Mockito.mock(LocationMapper.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final EventAdminService service = new EventAdminServiceImpl(mockRepository, mockCategoryRepository, mockLocationRepository, mockMapper, mockLocationMapper, formatter);

    @Test
    void findAllNormal() {
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
        when(mockRepository.findAllAdminWithCriteria(any(), any(), any(), any(), any(), any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);

        Assertions.assertEquals(List.of(eventDto), service.findAll(0, 10, List.of(1),
                List.of("PUBLISHED"), List.of(1), "2011-11-11 11:11", "2011-12-11 11:11"));
        Mockito.verify(mockRepository, times(1))
                .findAllAdminWithCriteria(any(), any(), any(), any(), any(), any());
    }

    @Test
    void findAllNormalWrongPeriod() {
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.findAll(0, 10, List.of(1),
                List.of("PUBLISHED"), List.of(1), "2012-11-11 11:11", "2011-12-11 11:11"));

        Assertions.assertEquals("Дата начала не может быть позже даты конца.", exception.getMessage());
        Mockito.verify(mockRepository, times(0))
                .findAllAdminWithCriteria(any(), any(), any(), any(), any(), any());
    }

    @Test
    void updateNormal() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventAdminRequest dto = UpdateEventAdminRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .category(1)
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .location(locationDto)
                .requestModeration(true)
                .stateAction(StateAction.PUBLISH_EVENT.name())
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
        Event eventUpdated = Event.builder()
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
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        when(mockMapper.toDto(any()))
                .thenReturn(eventDto);
        when(mockRepository.save(any()))
                .thenReturn(eventUpdated);

        Assertions.assertEquals(eventDto, service.update(dto, 1));
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void updateNormalWrongDate() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventAdminRequest dto = UpdateEventAdminRequest.builder()
                .eventDate("2022-11-11 11:11")
                .annotation("testAnnotation")
                .category(1)
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .location(locationDto)
                .requestModeration(true)
                .stateAction(StateAction.PUBLISH_EVENT.name())
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
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.update(dto, 1));

        Assertions.assertEquals("До даты события должно быть не менее чем 2 часа.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void updateNormalWrongCategory() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventAdminRequest dto = UpdateEventAdminRequest.builder()
                .eventDate("2024-11-11 11:11")
                .annotation("testAnnotation")
                .category(1)
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .location(locationDto)
                .requestModeration(true)
                .stateAction(StateAction.PUBLISH_EVENT.name())
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
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CategoryNotFound exception = Assertions.assertThrows(CategoryNotFound.class, () -> service.update(dto, 1));

        Assertions.assertEquals("Категория с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void updateNormalWrongNewEventDate() {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lat(11.11)
                .lon(22.22)
                .build();
        UpdateEventAdminRequest dto = UpdateEventAdminRequest.builder()
                .eventDate("2022-11-11 11:11")
                .annotation("testAnnotation")
                .category(1)
                .paid(true)
                .title("testTitle")
                .description("testDescription")
                .participantLimit(20)
                .location(locationDto)
                .requestModeration(true)
                .stateAction(StateAction.PUBLISH_EVENT.name())
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
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(event));
        when(mockCategoryRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        BadParameter exception = Assertions.assertThrows(BadParameter.class, () -> service.update(dto, 1));

        Assertions.assertEquals("До новой даты события должно быть не менее чем 2 часа.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

}