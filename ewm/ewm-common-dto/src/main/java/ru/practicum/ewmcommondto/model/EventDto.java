package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EventDto {

    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String annotation;

    private UserDto initiator;

    private Integer confirmedRequests;

    @NotNull
    private CategoryDto category;

    private LocalDateTime createdOn;

    @NotBlank
    private String description;

    @Future
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private LocationDto location;

    private boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private String state;

    private String stateAction;

    private Integer views;

}