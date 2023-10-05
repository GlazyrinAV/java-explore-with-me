package ru.practicum.ewmclient.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewmclient.model.location.LocationDto;

import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NewEventDto {

    @NotBlank
    private String title;

    @NotBlank
    private String annotation;

    @NotBlank
    private String description;

    private String eventDate;

    @NotNull
    private LocationDto location;

    private Boolean paid = false;

    private Boolean requestModeration = true;

    @Positive
    private int category;

    private Integer participantLimit = 0;

}