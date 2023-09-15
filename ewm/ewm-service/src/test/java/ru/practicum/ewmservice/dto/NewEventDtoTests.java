package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.LocationDto;
import ru.practicum.ewmclient.model.NewEventDto;

import java.io.IOException;

@JsonTest
public class NewEventDtoTests {

    @Autowired
    private JacksonTester<NewEventDto> json;

    @Test
    void newEventDto() throws IOException {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lon(11.11)
                .lat(22.22)
                .build();
        NewEventDto newEventDto = NewEventDto.builder()
                .eventDate("2011-11-11 11:11")
                .annotation("testAnnotation")
                .category(1)
                .description("testDescription")
                .paid(true)
                .participantLimit(0)
                .requestModeration(false)
                .location(locationDto)
                .title("testTitle")
                .build();

        JsonContent<NewEventDto> result = json.write(newEventDto);
        Assertions.assertEquals("{\"title\":\"testTitle\",\"annotation\":\"testAnnotation\"," +
                        "\"description\":\"testDescription\",\"eventDate\":\"2011-11-11 11:11\"," +
                        "\"location\":{\"id\":1,\"lat\":22.22,\"lon\":11.11},\"paid\":true,\"requestModeration\":false," +
                        "\"category\":1,\"participantLimit\":0}", result.getJson(),
                "Ошибка в обработке NewEventDto.");
    }

}