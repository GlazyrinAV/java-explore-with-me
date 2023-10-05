package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmclient.model.compilation.CompilationDto;
import ru.practicum.ewmclient.model.compilation.NewCompilationDto;
import ru.practicum.ewmclient.model.compilation.UpdateCompilationRequest;

public interface CompilationAdminService {

    CompilationDto save(NewCompilationDto dto);

    CompilationDto update(UpdateCompilationRequest dto, int compId);

    void remove(int compId);

}