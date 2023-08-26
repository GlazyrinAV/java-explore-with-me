package ru.practicum.statsclient.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsclient.client.StatsClient;
import ru.practicum.statscommondto.StatsDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {

    public final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveStats(@Valid @RequestBody StatsDto dto) {
        return statsClient.saveStats(dto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> findStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam (required = false) String[] uris,
                                           @RequestParam (required = false, defaultValue = "false") boolean unique) {
        return statsClient.findStats(start, end, uris, unique);
    }

    @GetMapping("/stats/{eventId}")
    public ResponseEntity<Object> findStats(@PathVariable int eventId) {
        return statsClient.findStatsForEwm(eventId);
    }

}