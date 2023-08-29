package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmclient.model.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {

    Collection<CompilationDto> findAll(Boolean pinned, int from, int size);

    CompilationDto findById(int compId);

}