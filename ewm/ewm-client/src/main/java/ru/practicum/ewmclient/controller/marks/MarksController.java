package ru.practicum.ewmclient.controller.marks;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.marks.MarksClient;

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> save(@Positive @NotNull @PathVariable int userId,
                                       @Positive @NotNull @PathVariable int eventId,
                                       @RequestParam int mark,
                                       HttpServletRequest request) {
        return client.save(userId, eventId, mark, request);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Object> remove(@Positive @NotNull @PathVariable int userId,
                                         @Positive @NotNull @PathVariable int eventId,
                                         HttpServletRequest request) {
        return client.remove(userId, eventId, request);
    }

}