package ru.practicum.ewmclient.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.event.EventAdminClient;
import ru.practicum.ewmcommondto.model.EventDto;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {

    private final EventAdminClient client;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return client.findAll();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody EventDto dto,
                                         @PathVariable int id) {
        return client.update(dto, id);
    }

}