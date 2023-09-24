package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.ewmclient.model.*;
import ru.practicum.ewmservice.exceptions.exceptions.CompilationNotFound;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.mapper.CompilationMapper;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.service.compilation.CompilationAdminService;
import ru.practicum.ewmservice.service.compilation.CompilationAdminServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CompilationAdminTests {

    private final CompilationRepository mockRepository = Mockito.mock(CompilationRepository.class);

    private final EventRepository mockEventRepository = Mockito.mock(EventRepository.class);

    private final CompilationMapper mockMapper = Mockito.mock(CompilationMapper.class);

    private final CompilationAdminService service = new CompilationAdminServiceImpl(mockRepository, mockEventRepository, mockMapper);

    @Test
    void save() {
        NewCompilationDto newDto = NewCompilationDto.builder()
                .events(List.of(1))
                .pinned(true)
                .title("testCompilationTitle")
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
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of(eventDto))
                .title("testCompilationTitle")
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
                .title("testCompilationTitle")
                .build();
        when(mockRepository.save(any()))
                .thenReturn(compilation);
        when(mockEventRepository.findAllWithCriteria(any()))
                .thenReturn(List.of(event));
        when(mockMapper.toDto(any()))
                .thenReturn(compilationDto);

        Assertions.assertEquals(compilationDto, service.save(newDto));
        Mockito.verify(mockEventRepository, times(1)).findAllWithCriteria(any());
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void saveWithoutEvents() {
        NewCompilationDto newDto = NewCompilationDto.builder()
                .events(List.of())
                .pinned(true)
                .title("testCompilationTitle")
                .build();
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1)
                .pinned(true)
                .events(List.of())
                .title("testCompilationTitle")
                .build();
        Compilation compilation = Compilation.builder()
                .id(1)
                .pinned(true)
                .events(List.of())
                .title("testCompilationTitle")
                .build();
        when(mockRepository.save(any()))
                .thenReturn(compilation);
        when(mockMapper.toDto(any()))
                .thenReturn(compilationDto);

        Assertions.assertEquals(compilationDto, service.save(newDto));
        Mockito.verify(mockEventRepository, times(0)).findAllWithCriteria(any());
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void update() {
        UpdateCompilationRequest dto = UpdateCompilationRequest.builder()
                .events(List.of(1))
                .pinned(true)
                .title("updateTitle")
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
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(compilation));
        when(mockRepository.save(any()))
                .thenReturn(compilation);
        when(mockEventRepository.findAllWithCriteria(any()))
                .thenReturn(List.of(event));
        when(mockMapper.toDto(any()))
                .thenReturn(compilationDto);

        Assertions.assertEquals(compilationDto, service.update(dto, 1));
        Mockito.verify(mockEventRepository, times(1)).findAllWithCriteria(any());
        Mockito.verify(mockRepository, times(1)).save(any());
    }

    @Test
    void updateNotFound() {
        UpdateCompilationRequest dto = UpdateCompilationRequest.builder()
                .events(List.of(1))
                .pinned(true)
                .title("updateTitle")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CompilationNotFound exception = Assertions.assertThrows(CompilationNotFound.class, () -> service.update(dto, 1));

        Assertions.assertEquals("Подборка с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockEventRepository, times(0)).findAllWithCriteria(any());
        Mockito.verify(mockRepository, times(0)).save(any());
    }

    @Test
    void remove() {
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Compilation()));
        service.remove(1);

        Mockito.verify(mockRepository, times(1)).deleteById(1);
    }

    @Test
    void removeNotFound() {
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CompilationNotFound exception = Assertions.assertThrows(CompilationNotFound.class, () -> service.remove(1));

        Assertions.assertEquals("Подборка с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).deleteById(1);
    }

}