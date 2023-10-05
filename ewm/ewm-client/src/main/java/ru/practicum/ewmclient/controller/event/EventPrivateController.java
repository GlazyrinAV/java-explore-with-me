package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventPrivateClient;
import ru.practicum.ewmclient.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewmclient.model.event.NewEventDto;
import ru.practicum.ewmclient.model.event.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> save(@RequestBody @Valid NewEventDto dto,
                                       @NotNull @PathVariable int userId,
                                       HttpServletRequest request) {
        return client.save(dto, userId, request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> findAllByUserId(@PathVariable int userId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                  @Positive @RequestParam(defaultValue = "10") int size,
                                                  HttpServletRequest request) {
        return client.findAllByUserId(userId, from, size, request);
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> findById(@Positive @NotNull @PathVariable int userId,
                                           @Positive @PathVariable int eventId,
                                           HttpServletRequest request) {
        return client.findById(userId, eventId, request);
    }

    @GetMapping("/{eventId}/requests")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> findRequests(@Positive @PathVariable int userId,
                                               @Positive @PathVariable int eventId,
                                               HttpServletRequest request) {
        return client.findRequests(userId, eventId, request);
    }

    @PatchMapping("/{eventId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> update(@RequestBody UpdateEventUserRequest dto,
                                         @Positive @PathVariable int userId,
                                         @Positive @PathVariable int eventId,
                                         HttpServletRequest request) {
        return client.update(dto, userId, eventId, request);
    }

    @PatchMapping("/{eventId}/requests")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> updateRequests(@RequestBody @Valid EventRequestStatusUpdateRequest dto,
                                                 @Positive @PathVariable int userId,
                                                 @Positive @PathVariable int eventId,
                                                 HttpServletRequest request) {
        return client.updateRequests(dto, userId, eventId, request);
    }

}