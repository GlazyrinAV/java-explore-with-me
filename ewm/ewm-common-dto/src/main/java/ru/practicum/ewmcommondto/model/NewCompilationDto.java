package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    @NotBlank
    private String title;

    private Boolean pinned;

    Collection<Integer> events;

}