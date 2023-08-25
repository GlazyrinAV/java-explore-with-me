package ru.practicum.ewmservice.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmcommondto.model.UpdateEventAdminRequest;
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
    public Collection<EventDto> findAll(@RequestParam int from,
                                        @RequestParam int size,
                                        @RequestParam(required = false) Integer[] users,
                                        @RequestParam(required = false) String[] states,
                                        @RequestParam(required = false) Integer[] categories,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd) {
        return service.findAll(from, size, users, states, categories, rangeStart, rangeEnd);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto update(@RequestBody UpdateEventAdminRequest dto,
                           @PathVariable int id) {
        return service.update(dto, id);
    }

}