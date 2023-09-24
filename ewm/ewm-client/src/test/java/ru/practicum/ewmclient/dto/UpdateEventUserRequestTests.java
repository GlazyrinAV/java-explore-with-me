package ru.practicum.ewmclient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.LocationDto;
import ru.practicum.ewmclient.model.UpdateEventUserRequest;

import java.io.IOException;

@JsonTest
class UpdateEventUserRequestTests {

    @Autowired
    private JacksonTester<UpdateEventUserRequest> json;

    @Test
    void updateEventUserRequest() throws IOException {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lon(11.11)
                .lat(22.22)
                .build();
        UpdateEventUserRequest dto = UpdateEventUserRequest.builder()
                .category(1)
                .eventDate("2010-10-10 10:10")
                .annotation("updateAnnotation")
                .description("updateDescription")
                .location(locationDto)
                .paid(false)
                .requestModeration(true)
                .participantLimit(10)
                .stateAction("SEND_TO_REVIEW")
                .build();

        JsonContent<UpdateEventUserRequest> result = json.write(dto);
        Assertions.assertEquals("{\"annotation\":\"updateAnnotation\",\"description\":\"updateDescription\"," +
                        "\"eventDate\":\"2010-10-10 10:10\",\"location\":{\"id\":1,\"lat\":22.22,\"lon\":11.11}," +
                        "\"paid\":false,\"requestModeration\":true,\"category\":1,\"participantLimit\":10," +
                        "\"stateAction\":\"SEND_TO_REVIEW\"}", result.getJson(),
                "Ошибка в обработке UpdateEventUserRequest.");
    }

}