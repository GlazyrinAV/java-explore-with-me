package ru.practicum.ewmservice.controller.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.CategoryDto;
import ru.practicum.ewmservice.service.categories.CategoryAdminService;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryAdminService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto save(@RequestBody CategoryDto dto) {
        return service.save(dto);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto update(@RequestBody CategoryDto dto,
                              @PathVariable int catId) {
        return service.update(dto, catId);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable int catId) {
        service.remove(catId);
    }

}