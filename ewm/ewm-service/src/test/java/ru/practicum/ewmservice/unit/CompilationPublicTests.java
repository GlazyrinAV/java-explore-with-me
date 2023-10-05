package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmclient.model.category.CategoryDto;
import ru.practicum.ewmclient.model.compilation.CompilationDto;
import ru.practicum.ewmclient.model.event.EventDto;
import ru.practicum.ewmclient.model.location.LocationDto;
import ru.practicum.ewmclient.model.user.UserDto;
import ru.practicum.ewmservice.exceptions.exceptions.CompilationNotFound;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.CompilationMapper;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.service.compilation.CompilationPublicService;
import ru.practicum.ewmservice.service.compilation.CompilationPublicServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CompilationPublicTests {

    private final CompilationRepository mockRepository = Mockito.mock(CompilationRepository.class);

    private final CompilationMapper mockMapper = Mockito.mock(CompilationMapper.class);

    private final CompilationPublicService service = new CompilationPublicServiceImpl(mockRepository, mockMapper);

    @Test
    void findAllPinned() {
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
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("updateTitle")
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
        Compilation compilation = Compilation.builder()
                .id(1)
                .pinned(true)
                .events(List.of(event))
                .title("updateTitle")
                .build();
        Page<Compilation> page = new PageImpl<>(List.of(compilation));
        when(mockRepository.findAllByPinned(anyBoolean(), any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(compilationDto);

        Assertions.assertEquals(List.of(compilationDto), service.findAll(true, 0, 10));
        Mockito.verify(mockRepository, times(1)).findAllByPinned(anyBoolean(), any());
    }

    @Test
    void findAllWithoutPinned() {
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
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("updateTitle")
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
        Compilation compilation = Compilation.builder()
                .id(1)
                .pinned(true)
                .events(List.of(event))
                .title("updateTitle")
                .build();
        Page<Compilation> page = new PageImpl<>(List.of(compilation));
        when(mockRepository.findAll((Pageable) any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(compilationDto);

        Assertions.assertEquals(List.of(compilationDto), service.findAll(null, 0, 10));
        Mockito.verify(mockRepository, times(1)).findAll((Pageable) any());
    }

    @Test
    void findByIdNormal() {
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
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("updateTitle")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Compilation()));
        when(mockMapper.toDto(any()))
                .thenReturn(compilationDto);

        Assertions.assertEquals(compilationDto, service.findById(1));
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    void findByIdNotFound() {
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CompilationNotFound exception = Assertions.assertThrows(CompilationNotFound.class, () -> service.findById(1));

        Assertions.assertEquals("Подборка с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

}