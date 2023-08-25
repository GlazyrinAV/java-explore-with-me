package ru.practicum.ewmservice.controller.categories;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.CategoryDto;
import ru.practicum.ewmservice.service.categories.CategoryPublicService;

import java.util.Collection;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryPublicController {

    private final CategoryPublicService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CategoryDto> findAll(@RequestParam int from,
                                           @RequestParam int size) {
        return service.findAll(from, size);
    }

    @GetMapping("{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto findById(@PathVariable int catId) {
        return service.findById(catId);
    }

}