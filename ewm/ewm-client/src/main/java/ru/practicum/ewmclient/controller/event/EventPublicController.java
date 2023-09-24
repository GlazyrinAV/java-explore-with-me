package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventPublicClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPublicController {

    private final EventPublicClient client;

    @GetMapping
    public ResponseEntity<Object> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false) String text,
                                          @RequestParam(required = false) Collection<Integer> categories,
                                          @RequestParam(required = false) Boolean paid,
                                          @RequestParam(required = false) String rangeStart,
                                          @RequestParam(required = false) String rangeEnd,
                                          @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                          @RequestParam(required = false) String sort,
                                          HttpServletRequest request) {
        return client.findAll(from, size, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Positive @NotNull @PathVariable int id, HttpServletRequest request) {
        return client.findById(id, request);
    }

}