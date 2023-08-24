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
public class EventDto {

    private int id;

    private String title;

    private String annotation;

    private UserDto initiator;

    private Integer confirmedRequests;

    private CategoryDto category;

    private LocalDateTime createdOn;

    private String description;

    private LocalDateTime eventDate;

    private LocationDto location;

    private boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private String state;

    private Integer views;

}