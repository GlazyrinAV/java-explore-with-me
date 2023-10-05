package ru.practicum.ewmclient.controller.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.categories.CategoryAdminClient;
import ru.practicum.ewmclient.model.category.CategoryDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryAdminController {

    private final CategoryAdminClient client;

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> save(@RequestBody @Valid CategoryDto dto,
                                       HttpServletRequest request) {
        return client.save(dto, request);
    }

    @PatchMapping("/{catId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> update(@RequestBody @Valid CategoryDto dto,
                                         @PathVariable @NotNull int catId,
                                         HttpServletRequest request) {
        return client.update(dto, catId, request);
    }

    @DeleteMapping("/{catId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> remove(@PathVariable @NotNull int catId,
                                         HttpServletRequest request) {
        return client.remove(catId, request);
    }

}