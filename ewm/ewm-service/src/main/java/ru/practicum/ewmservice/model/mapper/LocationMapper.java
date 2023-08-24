package ru.practicum.ewmservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmcommondto.model.LocationDto;
import ru.practicum.ewmservice.model.Location;

@Component
public class LocationMapper {

    public Location fromDto(LocationDto dto) {
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }

    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

}