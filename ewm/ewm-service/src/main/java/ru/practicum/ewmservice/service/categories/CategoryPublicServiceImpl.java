package ru.practicum.ewmservice.service.categories;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.CategoryNotFound;
import ru.practicum.ewmcommondto.model.CategoryDto;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;


    @Override
    public Collection<CategoryDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(int catId) {
        return mapper.toDto(repository.findById(catId).orElseThrow(() -> new CategoryNotFound(catId)));
    }

}