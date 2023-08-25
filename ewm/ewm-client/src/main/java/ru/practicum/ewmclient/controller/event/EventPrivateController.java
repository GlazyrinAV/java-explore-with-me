package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventPrivateClient;
import ru.practicum.ewmcommondto.model.EventDto;
import ru.practicum.ewmcommondto.model.EventRequestStatusUpdateRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {

    private final EventPrivateClient client;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid EventDto dto,
                                       @PathVariable int userId) {
        return client.save(dto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByUserId(@PathVariable int userId) {
        return client.findAllByUserId(userId);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> findById(@PathVariable int userId,
                             @PathVariable int eventId) {
        return client.findById(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> findRequests(@PathVariable int userId,
                                               @PathVariable int eventId) {
        return client.findRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> update(@RequestBody EventDto dto,
                                         @PathVariable int userId,
                                         @PathVariable int eventId) {
        return client.update(dto, userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<Object> updateRequests(@RequestBody EventRequestStatusUpdateRequest dto,
                                                 @PathVariable int userId,
                                                 @PathVariable int eventId) {
        return client.updateRequests(dto, userId, eventId);
    }

}