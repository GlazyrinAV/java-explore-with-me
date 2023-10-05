package ru.practicum.ewmservice.controller.requests;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.model.request.ParticipationRequestDto;
import ru.practicum.ewmservice.service.requests.ParticipationRequestsPrivateService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestsPrivateController {

    private final ParticipationRequestsPrivateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto save(@PathVariable int userId,
                                        @RequestParam int eventId) {
        return service.save(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ParticipationRequestDto> findAll(@PathVariable int userId) {
        return service.findAll(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancel(@PathVariable int userId,
                                          @PathVariable int requestId) {
        return service.cancel(userId, requestId);
    }

}