package ru.practicum.ewmservice.service.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exceptions.exceptions.CategoryNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmclient.model.CategoryDto;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository repository;

    private final EventRepository eventRepository;

    public final CategoryMapper mapper;

    @Override
    public CategoryDto save(CategoryDto dto) {
        if (repository.findByName(dto.getName()) != null) {
            throw new WrongParameter("Категория с таким именем уже существует.");
        }
        return mapper.toDto(repository.save(mapper.fromDto(dto)));
    }

    @Override
    public CategoryDto update(CategoryDto dto, int catId) {
        Category categoryFromDb = repository.findById(catId).orElseThrow(() -> new CategoryNotFound(catId));
        if (!dto.getName().isBlank()) {
            if (repository.findDuplicate(dto.getName(), catId) != null) {
                throw new WrongParameter("Категория с таким именем уже существует.");
            } else if (dto.getName().equals(categoryFromDb.getName())) {
                return mapper.toDto(categoryFromDb);
            }
            categoryFromDb.setName(dto.getName());
        }
        return mapper.toDto(repository.save(categoryFromDb));
    }

    @Override
    public void remove(int catId) {
        repository.findById(catId).orElseThrow(() -> new CategoryNotFound(catId));
        if (!eventRepository.findByCategoryId(catId).isEmpty()) {
            throw new WrongParameter("Существуют события, связанные с категорией");
        }
        repository.deleteById(catId);
    }

}