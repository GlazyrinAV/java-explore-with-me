package ru.practicum.ewmservice.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmcommondto.exceptions.CompilationNotFound;
import ru.practicum.ewmcommondto.exceptions.EventNotFound;
import ru.practicum.ewmcommondto.model.CompilationDto;
import ru.practicum.ewmcommondto.model.NewCompilationDto;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.mapper.CompilationMapper;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationRepository repository;

    private final EventRepository eventRepository;

    private final CompilationMapper mapper;

    @Override
    public CompilationDto save(NewCompilationDto dto) {
        Compilation compilation = Compilation.builder()
                .pinned(dto.isPinned())
                .title(dto.getTitle())
                .build();
        if (!dto.getEvents().isEmpty()) {
            Collection<Event> eventsFromDto = getEventsFromList(dto.getEvents());
            compilation.setEvents(eventsFromDto);
        }
        return mapper.toDto(repository.save(compilation));
    }

    @Override
    public CompilationDto update(NewCompilationDto dto, int compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() -> new CompilationNotFound(compId));
        if (!dto.getTitle().isBlank()) {
            compilation.setTitle(dto.getTitle());
        }
        compilation.setPinned(dto.isPinned());
        if (!dto.getEvents().isEmpty()) {
            Collection<Event> eventsFromDto = getEventsFromList(dto.getEvents());
            compilation.setEvents(eventsFromDto);
        }
        return mapper.toDto(repository.save(compilation));
    }

    @Override
    public void remove(int compId) {
        repository.deleteById(compId);
    }

    private Collection<Event> getEventsFromList(Collection<Integer> list) {
        Collection<Event> eventsFromDto = new ArrayList<>();
        for (int eventId : list) {
            eventsFromDto.add(eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId)));
        }
        return eventsFromDto;
    }

}