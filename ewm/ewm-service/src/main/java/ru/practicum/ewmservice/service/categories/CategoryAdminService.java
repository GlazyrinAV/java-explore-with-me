package ru.practicum.ewmservice.service.categories;

import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.ewmcommondto.model.CategoryDto;

public interface CategoryAdminService {

    CategoryDto save(CategoryDto dto);

    CategoryDto update(CategoryDto dto, int catId);

    void remove(int catId);

}