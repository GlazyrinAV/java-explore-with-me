package ru.practicum.ewmclient.model.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewmclient.model.location.LocationDto;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateEventUserRequest {

    private String title;

    private String annotation;

    private String description;

    private String eventDate;

    private LocationDto location;

    private Boolean paid;

    private Boolean requestModeration;

    private Integer category;

    private Integer participantLimit;

    private String stateAction;

}