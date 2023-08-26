package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventAdminClient;
import ru.practicum.ewmcommondto.model.UpdateEventAdminRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventAdminController {

    private final EventAdminClient client;

    @GetMapping
    public ResponseEntity<Object> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false) Collection<Integer> users,
                                          @RequestParam(required = false) Collection<String> states,
                                          @RequestParam(required = false) Collection<Integer> categories,
                                          @RequestParam(required = false) String rangeStart,
                                          @RequestParam(required = false) String rangeEnd) {
        return client.findAll(from, size, users, states, categories, rangeStart, rangeEnd);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody UpdateEventAdminRequest dto,
                                         @Positive @PathVariable int id) {
        return client.update(dto, id);
    }

}