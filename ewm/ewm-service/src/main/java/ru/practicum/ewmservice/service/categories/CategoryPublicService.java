package ru.practicum.ewmservice.service.categories;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewmclient.model.category.CategoryDto;

import java.util.Collection;

public interface CategoryPublicService {

    Collection<CategoryDto> findAll(int from, int size);

    CategoryDto findById(@PathVariable int catId);

}