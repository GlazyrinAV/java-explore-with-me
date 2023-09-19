package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmclient.model.CategoryDto;
import ru.practicum.ewmservice.exceptions.exceptions.CategoryNotFound;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.service.categories.CategoryPublicService;
import ru.practicum.ewmservice.service.categories.CategoryPublicServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CategoryPublicTests {

    private final CategoryRepository mockRepository = Mockito.mock(CategoryRepository.class);

    private final CategoryMapper mockMapper = Mockito.mock(CategoryMapper.class);

    private final CategoryPublicService service = new CategoryPublicServiceImpl(mockRepository, mockMapper);

    @Test
    void findAll() {
        CategoryDto toDto1 = CategoryDto.builder()
                .id(1)
                .name("testCategory1")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory1")
                .build();
        Page<Category> page = new PageImpl<>(List.of(category));

        when(mockRepository.findAll((Pageable) any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(toDto1);

        Assertions.assertEquals(List.of(toDto1), service.findAll(0, 10));
        Mockito.verify(mockRepository, times(1)).findAll((Pageable) any());
    }

    @Test
    void findById() {
        CategoryDto toDto1 = CategoryDto.builder()
                .id(1)
                .name("testCategory1")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("testCategory1")
                .build();

        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(category));
        when(mockMapper.toDto(any()))
                .thenReturn(toDto1);

        Assertions.assertEquals(toDto1, service.findById(1));
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    void findByIdNull() {
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CategoryNotFound exception = Assertions.assertThrows(CategoryNotFound.class, () -> service.findById(1));

        Assertions.assertEquals("Категория с ID 1 не найдена.", exception.getMessage());
        Mockito.verify(mockRepository, times(1)).findById(1);
    }

}