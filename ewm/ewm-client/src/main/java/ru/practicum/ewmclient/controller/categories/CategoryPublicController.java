package ru.practicum.ewmclient.controller.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.categories.CategoryPublicClient;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryPublicController {

    private final CategoryPublicClient client;

    @GetMapping
    public ResponseEntity<Object> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size) {
        return client.findAll(from, size);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Object> findById(@PathVariable int catId) {
        return client.findById(catId);
    }

}