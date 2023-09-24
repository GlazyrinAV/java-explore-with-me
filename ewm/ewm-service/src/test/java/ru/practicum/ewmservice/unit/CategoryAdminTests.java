package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.ewmclient.model.CategoryDto;
import ru.practicum.ewmservice.exceptions.exceptions.CategoryNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.service.categories.CategoryAdminService;
import ru.practicum.ewmservice.service.categories.CategoryAdminServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CategoryAdminTests {

    private final CategoryRepository mockRepository = Mockito.mock(CategoryRepository.class);

    private final EventRepository mockEventRepository = Mockito.mock(EventRepository.class);

    private final CategoryMapper mockMapper = Mockito.mock(CategoryMapper.class);

    private final CategoryAdminService service = new CategoryAdminServiceImpl(mockRepository, mockEventRepository, mockMapper);

    @Test
    void save() {
        CategoryDto toDto = CategoryDto.builder()
                .name("testCategory")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        CategoryDto fromDto = CategoryDto.builder()
                .id(1)
                .name("testCategory")
                .build();
        when(mockRepository.findByName(any()))
                .thenReturn(null);
        when(mockMapper.fromDto(any()))
                .thenReturn(category);
        when((mockMapper.toDto(any())))
                .thenReturn(fromDto);
        when(mockEventRepository.save(any()))
                .thenReturn(category);

        Assertions.assertEquals(fromDto, service.save(toDto));
        Mockito.verify(mockRepository, times(1)).findByName("testCategory");
        Mockito.verify(mockRepository, times(1)).save(category);
    }

    @Test
    void saveDuplicate() {
        CategoryDto toDto = CategoryDto.builder()
                .name("testCategory")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        when(mockRepository.findByName(any()))
                .thenReturn(category);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(toDto));

        Assertions.assertEquals("Категория с таким именем уже существует.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findByName("testCategory");
        Mockito.verify(mockRepository, times(0)).save(category);
    }

    @Test
    void update() {
        CategoryDto toDto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("updateCategory")
                .build();
        CategoryDto fromDto = CategoryDto.builder()
                .id(1)
                .name("updateCategory")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(mockRepository.findDuplicate(any(), anyInt()))
                .thenReturn(null);
        when(mockRepository.save(category))
                .thenReturn(category);
        when(mockMapper.toDto(any()))
                .thenReturn(fromDto);

        Assertions.assertEquals(fromDto, service.update(toDto, 1));
        Mockito.verify(mockRepository, times(1)).findById(1);
        Mockito.verify(mockRepository, times(1)).findDuplicate("updateCategory", 1);
    }

    @Test
    void updateNotFound() {
        CategoryDto toDto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("updateCategory")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CategoryNotFound exception = Assertions.assertThrows(CategoryNotFound.class, () -> service.update(toDto, 1));

        Assertions.assertEquals("Категория с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(1);
        Mockito.verify(mockRepository, times(0)).findDuplicate("updateCategory", 1);
        Mockito.verify(mockRepository, times(0)).save(category);
    }

    @Test
    void updateDuplicate() {
        CategoryDto toDto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("updateCategory")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(mockRepository.findDuplicate(any(), anyInt()))
                .thenReturn(category);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.update(toDto, 1));

        Assertions.assertEquals("Категория с таким именем уже существует.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(1);
        Mockito.verify(mockRepository, times(1)).findDuplicate("updateCategory", 1);
        Mockito.verify(mockRepository, times(0)).save(category);
    }

    @Test
    void updateWithoutChange() {
        CategoryDto fromDto = CategoryDto.builder()
                .id(1)
                .name("updateCategory")
                .build();
        CategoryDto toDto = CategoryDto.builder()
                .name("updateCategory")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("updateCategory")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(mockRepository.findDuplicate(any(), anyInt()))
                .thenReturn(null);
        when(mockMapper.toDto(any()))
                .thenReturn(fromDto);

        Assertions.assertEquals(fromDto, service.update(toDto, 1));
        Mockito.verify(mockRepository, times(1)).findById(1);
        Mockito.verify(mockRepository, times(1)).findDuplicate("updateCategory", 1);
        Mockito.verify(mockRepository, times(0)).save(category);
    }

    @Test
    void remove() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(mockEventRepository.findByCategoryId(anyInt()))
                .thenReturn(List.of());

        service.remove(1);
        Mockito.verify(mockRepository, times(1)).deleteById(1);
    }

    @Test
    void removeNotFound() {
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CategoryNotFound exception = Assertions.assertThrows(CategoryNotFound.class, () -> service.remove(1));

        Assertions.assertEquals("Категория с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).deleteById(1);
    }

    @Test
    void removeWithEventsUsingCategory() {
        Category category = Category.builder()
                .id(1)
                .name("testCategory")
                .build();
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(mockEventRepository.findByCategoryId(anyInt()))
                .thenReturn(List.of(new Event()));

        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.remove(1));

        Assertions.assertEquals("Существуют события, связанные с категорией", exception.getMessage());
        Mockito.verify(mockRepository, times(0)).deleteById(1);
    }

}