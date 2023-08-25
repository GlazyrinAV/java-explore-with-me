package ru.practicum.ewmservice.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.service.event.EventPublicService;

import java.util.Collection;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final EventPublicService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventDto> findAll(@RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(required = false) String text,
                                        @RequestParam(required = false) Integer[] categories,
                                        @RequestParam(required = false) Boolean paid,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                        @RequestParam(required = false) String sort) {
        return service.findAll(from, size, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto findById(@PathVariable int id) {
        return service.findById(id);
    }

}