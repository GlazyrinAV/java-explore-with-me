package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmcommondto.model.CompilationDto;
import ru.practicum.ewmcommondto.model.NewCompilationDto;
import ru.practicum.ewmcommondto.model.UpdateCompilationRequest;

public interface CompilationAdminService {

    CompilationDto save(NewCompilationDto dto);

    CompilationDto update(UpdateCompilationRequest dto, int compId);

    void remove(int compId);

}