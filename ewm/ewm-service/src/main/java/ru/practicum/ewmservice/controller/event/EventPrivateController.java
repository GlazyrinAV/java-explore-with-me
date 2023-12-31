package ru.practicum.ewmservice.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.model.*;
import ru.practicum.ewmservice.service.event.EventPrivateService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    private final EventPrivateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto save(@RequestBody NewEventDto dto,
                         @PathVariable int userId) {
        return service.save(dto, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventDto> findAllByUserId(@PathVariable int userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return service.findAllByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto findById(@PathVariable int userId,
                             @PathVariable int eventId) {
        return service.findById(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ParticipationRequestDto> findRequests(@PathVariable int userId,
                                                            @PathVariable int eventId) {
        return service.findRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto update(@RequestBody UpdateEventUserRequest dto,
                           @PathVariable int userId,
                           @PathVariable int eventId) {
        return service.update(dto, userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateRequests(@RequestBody EventRequestStatusUpdateRequest dto,
                                                         @PathVariable int userId,
                                                         @PathVariable int eventId) {
        return service.updateRequests(dto, userId, eventId);
    }

}