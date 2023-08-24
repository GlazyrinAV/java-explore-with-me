package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    private String title;

    private boolean pinned;

    Collection<Integer> events;

}