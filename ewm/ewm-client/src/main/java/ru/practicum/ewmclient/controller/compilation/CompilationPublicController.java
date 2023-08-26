package ru.practicum.ewmclient.controller.compilation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.compilation.CompilationPublicClient;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompilationPublicController {

    private final CompilationPublicClient client;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(required = false) String pinned,
                                          @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size) {
        Boolean pinnedDto = convertPinned(pinned);
        return client.findAll(pinnedDto, from, size);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> findById(@Positive @PathVariable int compId) {
        return client.findById(compId);
    }

    private Boolean convertPinned(String pinned) {
        if (pinned == null) {
            return null;
        } else if (pinned.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

}