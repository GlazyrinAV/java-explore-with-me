package ru.practicum.ewmservice.service.categories;

import ru.practicum.ewmcommondto.model.CategoryDto;

public interface CategoryAdminService {

    CategoryDto save(CategoryDto dto);

    CategoryDto update(CategoryDto dto, int catId);

    void remove(int catId);

}