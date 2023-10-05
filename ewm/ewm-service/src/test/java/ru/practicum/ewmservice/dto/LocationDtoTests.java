package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.location.LocationDto;

import java.io.IOException;

@JsonTest
class LocationDtoTests {

    @Autowired
    private JacksonTester<LocationDto> json;

    @Test
    void locationDto() throws IOException {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lon(11.11)
                .lat(22.22)
                .build();

        JsonContent<LocationDto> result = json.write(locationDto);
        Assertions.assertEquals("{\"id\":1,\"lat\":22.22,\"lon\":11.11}", result.getJson(),
                "Ошибка в обработке LocationDto.");
    }

}