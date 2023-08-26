package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    private int id;

    private Integer event;

    private LocalDateTime created;

    private Integer requester;

    private String status;

}