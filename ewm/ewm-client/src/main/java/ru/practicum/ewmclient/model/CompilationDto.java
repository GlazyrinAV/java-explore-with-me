package ru.practicum.ewmclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    private int id;

    @NotBlank
    private String title;

    @NotNull
    private boolean pinned;

    private Collection<EventDto> events;

}