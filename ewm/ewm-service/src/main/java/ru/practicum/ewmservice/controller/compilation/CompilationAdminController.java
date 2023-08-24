package ru.practicum.ewmservice.controller.compilation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmcommondto.model.CompilationDto;
import ru.practicum.ewmcommondto.model.NewCompilationDto;
import ru.practicum.ewmservice.service.compilation.CompilationAdminService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationAdminService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto save(@RequestBody NewCompilationDto dto) {
        return service.save(dto);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto update(@RequestBody NewCompilationDto dto,
                                 @PathVariable int compId) {
        return service.update(dto, compId);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable int compId) {
        service.remove(compId);
    }

}