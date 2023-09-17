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
public class NewCompilationDto {

    @NotBlank
    private String title;

    @NotNull
    private Boolean pinned;

    Collection<Integer> events;

}