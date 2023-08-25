package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmcommondto.model.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {

    Collection<CompilationDto> findAll(boolean pinned, int from, int size);

    CompilationDto findById(int compId);

}