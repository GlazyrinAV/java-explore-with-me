package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

   Page<Compilation> findAllByPinned(Boolean pinned, Pageable page);

}