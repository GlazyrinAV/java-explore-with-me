package ru.practicum.ewmservice.controller.requests;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.service.requests.ParticipationRequestsPrivateService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{usersId}/requests")
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestsPrivateController {

    private final ParticipationRequestsPrivateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto save(@PathVariable int usersId,
                                        @RequestParam int eventId) {
        return service.save(usersId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ParticipationRequestDto> findAll(@PathVariable int usersId) {
        return service.findAll(usersId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancel(@PathVariable int usersId,
                                          @PathVariable int requestId) {
        return service.cancel(usersId, requestId);
    }

}