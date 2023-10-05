package ru.practicum.ewmservice.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmclient.model.compilation.CompilationDto;
import ru.practicum.ewmclient.model.event.EventDto;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public Compilation fromDto(CompilationDto dto) {
        Collection<Event> events = new ArrayList<>();
        if (dto.getEvents() != null) {
            events = dto.getEvents().stream().map(eventMapper::fromDto).collect(Collectors.toList());
        }
        return Compilation.builder()
                .title(dto.getTitle())
                .pinned(dto.isPinned())
                .events(events)
                .build();
    }

    public CompilationDto toDto(Compilation compilation) {
        Collection<EventDto> events = new ArrayList<>();
        if (compilation.getEvents() != null) {
            events = compilation.getEvents().stream().map(eventMapper::toShortDto).collect(Collectors.toList());
        }
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(events)
                .build();
    }

}