package ru.practicum.ewmclient.controller.marks;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.marks.MarksClient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/users/{userId}/events/{eventId}/like")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MarksController {

    private final MarksClient client;

    @PostMapping
    public ResponseEntity<Object> save(@Positive @NotNull @PathVariable int userId,
                                       @Positive @NotNull @PathVariable int eventId,
                                       @RequestParam int mark) {
        return client.save(userId, eventId, mark);
    }

    @DeleteMapping
    public ResponseEntity<Object> remove(@Positive @NotNull @PathVariable int userId,
                                         @Positive @NotNull @PathVariable int eventId) {
        return client.remove(userId, eventId);
    }

}