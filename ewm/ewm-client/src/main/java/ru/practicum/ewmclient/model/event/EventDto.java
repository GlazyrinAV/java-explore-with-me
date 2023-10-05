package ru.practicum.ewmclient.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewmclient.model.location.LocationDto;
import ru.practicum.ewmclient.model.user.UserDto;
import ru.practicum.ewmclient.model.category.CategoryDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    private String createdOn;

    @NotBlank
    private String description;

    @NotNull
    private String eventDate;

    @NotNull
    private LocationDto location;

    private boolean paid;

    private Integer participantLimit;

    private String publishedOn;

    private boolean requestModeration;

    private String state;

    private String stateAction;

    private Integer views;

    private BigDecimal mark;

}