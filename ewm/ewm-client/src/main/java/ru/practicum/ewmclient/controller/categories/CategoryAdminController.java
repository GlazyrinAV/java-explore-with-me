package ru.practicum.ewmclient.controller.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.categories.CategoryAdminClient;
import ru.practicum.ewmclient.model.CategoryDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryAdminController {

    private final CategoryAdminClient client;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid CategoryDto dto) {
        return client.save(dto);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<Object> update(@RequestBody @Valid CategoryDto dto,
                                         @PathVariable @NotNull int catId) {
        return client.update(dto, catId);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> remove(@PathVariable @NotNull int catId) {
        return client.remove(catId);
    }

}