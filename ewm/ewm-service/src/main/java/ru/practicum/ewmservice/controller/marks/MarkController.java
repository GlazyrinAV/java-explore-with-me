package ru.practicum.ewmservice.controller.marks;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.model.MarkDto;
import ru.practicum.ewmservice.service.marks.MarkService;

@RestController
@RequestMapping("/users/{userId}/events/{eventId}/like")
@RequiredArgsConstructor
@Slf4j
public class MarkController {

    private final MarkService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkDto save(@PathVariable int userId,
                        @PathVariable int eventId,
                        @RequestParam int mark) {
        return service.save(userId, eventId, mark);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void remove(@PathVariable int userId,
                        @PathVariable int eventId) {
        service.remove(userId, eventId);
    }

}