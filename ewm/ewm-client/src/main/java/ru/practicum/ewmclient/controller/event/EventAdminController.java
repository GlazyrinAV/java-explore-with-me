package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventAdminClient;
import ru.practicum.ewmcommondto.exceptions.WrongParameter;
import ru.practicum.ewmcommondto.model.UpdateEventAdminRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventAdminController {

    private final EventAdminClient client;

    @GetMapping
    public ResponseEntity<Object> findAll(@Positive @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false) Integer[] users,
                                          @RequestParam(required = false) String[] states,
                                          @RequestParam(required = false) Integer[] categories,
                                          @NotBlank @RequestParam(required = false) String rangeStart,
                                          @NotBlank @RequestParam(required = false) String rangeEnd) {
        return client.findAll(from, size, users, states, categories, rangeStart, rangeEnd);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody UpdateEventAdminRequest dto,
                                         @Positive @PathVariable int id) {
        if (dto.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new WrongParameter("До даты события должно быть не менее чем 2 часа.");
        }
        return client.update(dto, id);
    }

}