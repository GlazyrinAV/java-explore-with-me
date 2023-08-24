package ru.practicum.ewmservice.service.compilation;

import ru.practicum.ewmcommondto.model.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {

    Collection<CompilationDto> findAll();

    CompilationDto findById(int compId);

}