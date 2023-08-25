package ru.practicum.ewmcommondto.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateEventUserRequest {

    private String title;

    private String annotation;

    private String description;

    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Boolean requestModeration;

    private Integer category;

    private Integer participantLimit;

    private String stateAction;

}