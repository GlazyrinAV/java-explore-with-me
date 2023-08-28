package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmclient.model.CompilationDto;
import ru.practicum.ewmclient.model.NewCompilationDto;
import ru.practicum.ewmclient.model.UpdateCompilationRequest;

public interface CompilationAdminService {

    CompilationDto save(NewCompilationDto dto);

    CompilationDto update(UpdateCompilationRequest dto, int compId);

    void remove(int compId);

}