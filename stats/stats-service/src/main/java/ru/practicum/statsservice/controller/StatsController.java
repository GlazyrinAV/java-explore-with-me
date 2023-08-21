package ru.practicum.statsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statscommondto.model.dto.StatsDto;
import ru.practicum.statscommondto.model.dto.ViewStats;
import ru.practicum.statsservice.service.StatsService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStats(@RequestBody StatsDto dto) {
        service.saveStats(dto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public ViewStats findStats(@RequestParam LocalDateTime start,
                               @RequestParam LocalDateTime end,
                               @RequestParam(required = false) String uri,
                               @RequestParam(required = false) Boolean unique) {
        return service.findStats(start, end, uri, unique);
    }

}