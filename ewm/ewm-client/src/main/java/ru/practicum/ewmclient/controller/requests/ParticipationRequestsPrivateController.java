package ru.practicum.ewmclient.controller.requests;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.requests.ParticipationRequestsPrivateClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
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
    public ResponseEntity<Object> save(@Positive @NotNull @PathVariable int userId,
                                       @PositiveOrZero @NotNull @RequestParam int eventId,
                                       HttpServletRequest request) {
        return client.save(userId, eventId, request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@Positive @NotNull @PathVariable int userId,
                                          HttpServletRequest request) {
        return client.findAll(userId, request);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancel(@Positive @NotNull @PathVariable int userId,
                                         @Positive @NotNull @PathVariable int requestId,
                                         HttpServletRequest request) {
        return client.cancel(userId, requestId, request);
    }

}