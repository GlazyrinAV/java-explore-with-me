package ru.practicum.ewmclient.controller.compilation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.compilation.CompilationAdminClient;
import ru.practicum.ewmcommondto.model.NewCompilationDto;
import ru.practicum.ewmcommondto.model.UpdateCompilationRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompilationAdminController {

    private final CompilationAdminClient client;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid NewCompilationDto dto) {
        return client.save(dto);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<Object> update(@RequestBody UpdateCompilationRequest dto,
                                         @Positive @PathVariable int compId) {
        return client.update(dto, compId);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> remove(@Positive @PathVariable int compId) {
        return client.remove(compId);
    }

}