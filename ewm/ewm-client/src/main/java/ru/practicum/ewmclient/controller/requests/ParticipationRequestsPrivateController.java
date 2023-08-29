package ru.practicum.ewmclient.controller.requests;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.requests.ParticipationRequestsPrivateClient;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ParticipationRequestsPrivateController {

    private final ParticipationRequestsPrivateClient client;

    @PostMapping
    public ResponseEntity<Object> save(@Positive @PathVariable int userId,
                                       @PositiveOrZero @RequestParam int eventId) {
        return client.save(userId, eventId);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@Positive @PathVariable int userId) {
        return client.findAll(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancel(@Positive @PathVariable int userId,
                                         @Positive @PathVariable int requestId) {
        return client.cancel(userId, requestId);
    }

}