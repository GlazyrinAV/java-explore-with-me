package ru.practicum.ewmclient.controller.compilation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.compilation.CompilationPublicClient;
import ru.practicum.ewmcommondto.exceptions.BadParameter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicController {

    private final CompilationPublicClient client;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam String pinned,
                                          @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size) {
        boolean pinnedDto = isPinnedCorrect(pinned);
        return client.findAll(pinnedDto, from, size);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> findById(@Positive @PathVariable int compId) {
        return client.findById(compId);
    }

    private boolean isPinnedCorrect(String pinned) {
        if (pinned.equalsIgnoreCase("true")) {
            return true;
        } else if (pinned.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new BadParameter("Неправильно указан параметр pinned.");
        }
    }

}