package ru.practicum.ewmclient.model.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateCompilationRequest {

    Collection<Integer> events;

    Boolean pinned;

    String title;

}