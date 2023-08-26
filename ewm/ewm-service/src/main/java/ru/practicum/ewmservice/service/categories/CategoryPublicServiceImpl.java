package ru.practicum.ewmservice.service.categories;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmcommondto.exceptions.CategoryNotFound;
import ru.practicum.ewmcommondto.model.CategoryDto;
import ru.practicum.ewmservice.model.mapper.CategoryMapper;
import ru.practicum.ewmservice.repository.CategoryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;


    @Override
    public Collection<CategoryDto> findAll(int from, int size) {
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);
        return repository.findAll(page).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(int catId) {
        return mapper.toDto(repository.findById(catId).orElseThrow(() -> new CategoryNotFound(catId)));
    }

}