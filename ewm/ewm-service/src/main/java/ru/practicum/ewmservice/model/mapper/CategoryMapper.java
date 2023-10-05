package ru.practicum.ewmservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmclient.model.category.CategoryDto;
import ru.practicum.ewmservice.model.Category;

@Component
public class CategoryMapper {

    public Category fromDto(CategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}