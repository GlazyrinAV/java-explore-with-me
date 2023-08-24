package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmcommondto.model.CompilationDto;
import ru.practicum.ewmcommondto.model.NewCompilationDto;

public interface CompilationAdminService {

    CompilationDto save(NewCompilationDto dto);

    CompilationDto update(NewCompilationDto dto, int compId);

    void remove(int compId);

}