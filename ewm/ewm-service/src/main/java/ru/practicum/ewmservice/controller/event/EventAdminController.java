package ru.practicum.ewmservice.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmservice.service.event.EventAdminService;

import java.util.Collection;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {

    private final EventAdminService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventDto> findAll() {
        return service.findAll();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto update(@RequestBody EventDto dto,
                           @PathVariable int id) {
        return service.update(dto, id);
    }

}