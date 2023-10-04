package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventAdminClient;
import ru.practicum.ewmclient.model.UpdateEventAdminRequest;

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false) Collection<Integer> users,
                                          @RequestParam(required = false) Collection<String> states,
                                          @RequestParam(required = false) Collection<Integer> categories,
                                          @RequestParam(required = false) String rangeStart,
                                          @RequestParam(required = false) String rangeEnd,
                                          HttpServletRequest request) {
        return client.findAll(from, size, users, states, categories, rangeStart, rangeEnd, request);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> update(@RequestBody UpdateEventAdminRequest dto,
                                         @Positive @PathVariable int id,
                                         HttpServletRequest request) {
        return client.update(dto, id, request);
    }

}