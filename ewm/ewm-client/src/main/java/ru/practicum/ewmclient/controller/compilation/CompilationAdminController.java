package ru.practicum.ewmclient.controller.compilation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.compilation.CompilationAdminClient;
import ru.practicum.ewmclient.model.NewCompilationDto;
import ru.practicum.ewmclient.model.UpdateCompilationRequest;

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> save(@RequestBody @Valid NewCompilationDto dto,
                                       HttpServletRequest request) {
        return client.save(dto, request);
    }

    @PatchMapping("/{compId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> update(@RequestBody UpdateCompilationRequest dto,
                                         @Positive @PathVariable int compId,
                                         HttpServletRequest request) {
        return client.update(dto, compId, request);
    }

    @DeleteMapping("/{compId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Object> remove(@Positive @PathVariable int compId,
                                         HttpServletRequest request) {
        return client.remove(compId, request);
    }

}