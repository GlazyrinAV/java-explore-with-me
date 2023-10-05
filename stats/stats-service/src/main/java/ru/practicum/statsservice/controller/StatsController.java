package ru.practicum.statsservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsclient.dto.ViewStatsDto;
import ru.practicum.statsservice.service.stats.StatsService;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStats(@RequestBody JsonNode dto) {
        service.saveStats(dto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ViewStatsDto> findStats(@RequestParam String start,
                                              @RequestParam String end,
                                              @RequestParam(required = false) Collection<String> uris,
                                              @RequestParam(required = false, defaultValue = "false") boolean unique) {
        String startDto = URLDecoder.decode(start, Charset.defaultCharset());
        String endDto = URLDecoder.decode(end, Charset.defaultCharset());
        Collection<String> urisDto = new ArrayList<>();
        for (String uri : uris) {
            urisDto.add(URLDecoder.decode(uri, Charset.defaultCharset()));
        }
        return service.findStats(startDto, endDto, urisDto, unique);
    }

}