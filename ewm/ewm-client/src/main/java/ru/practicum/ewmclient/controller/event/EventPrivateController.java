package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventPrivateClient;
import ru.practicum.ewmclient.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewmclient.model.NewEventDto;
import ru.practicum.ewmclient.model.UpdateEventUserRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {

    private final EventPrivateClient client;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid NewEventDto dto,
                                       @PathVariable int userId) {
        return client.save(dto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByUserId(@PathVariable int userId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                  @Positive @RequestParam(defaultValue = "10") int size) {
        return client.findAllByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> findById(@Positive @PathVariable int userId,
                                           @Positive @PathVariable int eventId) {
        return client.findById(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> findRequests(@Positive @PathVariable int userId,
                                               @Positive @PathVariable int eventId) {
        return client.findRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> update(@RequestBody UpdateEventUserRequest dto,
                                         @Positive @PathVariable int userId,
                                         @Positive @PathVariable int eventId) {
        return client.update(dto, userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<Object> updateRequests(@RequestBody @Valid EventRequestStatusUpdateRequest dto,
                                                 @Positive @PathVariable int userId,
                                                 @Positive @PathVariable int eventId) {
        return client.updateRequests(dto, userId, eventId);
    }

}