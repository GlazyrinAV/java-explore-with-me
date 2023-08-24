package ru.practicum.ewmservice.service.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.CategoryNotFound;
import ru.practicum.ewmcommondto.model.CategoryDto;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository repository;

    public final CategoryMapper mapper;

    @Override
    public CategoryDto save(CategoryDto dto) {
        return mapper.toDto(repository.save(mapper.fromDto(dto)));
    }

    @Override
    public CategoryDto update(CategoryDto dto, int catId) {
        Category categoryFromDb = repository.findById(catId).orElseThrow(() -> new CategoryNotFound(catId));
        if (!dto.getName().isBlank()) {
            categoryFromDb.setName(dto.getName());
        }
        return mapper.toDto(repository.save(categoryFromDb));
    }

    @Override
    public void remove(int catId) {
        repository.findById(catId);
    }

}