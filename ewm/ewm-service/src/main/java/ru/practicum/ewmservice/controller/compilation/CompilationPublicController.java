package ru.practicum.ewmservice.controller.compilation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.model.compilation.CompilationDto;
import ru.practicum.ewmservice.service.compilation.CompilationPublicService;

import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicController {

    private final CompilationPublicService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CompilationDto> findAll(@RequestParam(required = false) Boolean pinned,
                                              @RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        return service.findAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto findById(@PathVariable int compId) {
        return service.findById(compId);
    }

}