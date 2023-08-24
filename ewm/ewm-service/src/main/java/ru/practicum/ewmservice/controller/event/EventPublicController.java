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
    public Collection<EventDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto findById(@PathVariable int id) {
        return service.findById(id);
    }

}