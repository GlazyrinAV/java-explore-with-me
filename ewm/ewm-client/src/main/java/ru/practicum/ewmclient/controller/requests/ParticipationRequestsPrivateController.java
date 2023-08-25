package ru.practicum.ewmclient.controller.requests;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.requests.ParticipationRequestsPrivateClient;

@RestController
@RequestMapping("/users/{usersId}/requests")
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestsPrivateController {

    private final ParticipationRequestsPrivateClient client;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable int usersId,
                                       @RequestParam int eventId) {
        return client.save(usersId, eventId);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@PathVariable int usersId) {
        return client.findAll(usersId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancel(@PathVariable int usersId,
                                         @PathVariable int requestId) {
        return client.cancel(usersId, requestId);
    }

}