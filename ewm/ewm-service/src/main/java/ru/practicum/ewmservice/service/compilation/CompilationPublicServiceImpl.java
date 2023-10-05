package ru.practicum.ewmservice.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exceptions.exceptions.CompilationNotFound;
import ru.practicum.ewmclient.model.compilation.CompilationDto;
import ru.practicum.ewmservice.model.mapper.CompilationMapper;
import ru.practicum.ewmservice.repository.CompilationRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {

    private final CompilationRepository repository;

    private final CompilationMapper mapper;

    @Override
    public Collection<CompilationDto> findAll(Boolean pinned, int from, int size) {
        Pageable page = PageRequest.of(from == 0 ? 0 : from / size, size);
        if (pinned != null) {
            return repository.findAllByPinned(pinned, page).stream().map(mapper::toDto).collect(Collectors.toList());
        }
        return repository.findAll(page).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto findById(int compId) {
        return mapper.toDto(repository.findById(compId).orElseThrow(() -> new CompilationNotFound(compId)));
    }

}