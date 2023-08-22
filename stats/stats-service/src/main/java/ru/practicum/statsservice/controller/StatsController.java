package ru.practicum.statsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statscommondto.StatsDto;
import ru.practicum.statscommondto.ViewStatsDto;
import ru.practicum.statsservice.service.StatsService;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
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
    public Collection<ViewStatsDto> findStats(@RequestParam String start,
                                              @RequestParam String end,
                                              @RequestParam(required = false) String[] uris,
                                              @RequestParam(required = false, defaultValue = "false") boolean unique) {
        LocalDateTime startDto = LocalDateTime.parse(URLDecoder.decode(start, Charset.defaultCharset()));
        LocalDateTime endDto = LocalDateTime.parse(URLDecoder.decode(end, Charset.defaultCharset()));
        return service.findStats(startDto, endDto, uris, unique);
    }

}