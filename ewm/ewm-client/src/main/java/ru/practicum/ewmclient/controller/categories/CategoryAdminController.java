package ru.practicum.ewmclient.controller.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.categories.CategoryAdminClient;
import ru.practicum.ewmcommondto.model.CategoryDto;

import javax.validation.Valid;

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
    public ResponseEntity<Object> update(@RequestBody CategoryDto dto,
                              @PathVariable int catId) {
        return client.update(dto, catId);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> remove(@PathVariable int catId) {
        return client.remove(catId);
    }

}