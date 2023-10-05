package ru.practicum.statsclient.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsclient.client.StatsClient;
import ru.practicum.statsclient.dto.StatsDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {

    public final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveStats(@Valid @RequestBody StatsDto dto,
                                            HttpServletRequest request) {
        return statsClient.saveStats(dto, request);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> findStats(@RequestParam @NotBlank String start,
                                            @RequestParam @NotBlank String end,
                                            @RequestParam(required = false) String[] uris,
                                            @RequestParam(required = false, defaultValue = "false") boolean unique,
                                            HttpServletRequest request) {
        return statsClient.findStats(start, end, uris, unique, request);
    }

}