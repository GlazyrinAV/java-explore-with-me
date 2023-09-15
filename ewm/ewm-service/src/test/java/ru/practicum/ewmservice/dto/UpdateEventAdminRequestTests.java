package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.LocationDto;
import ru.practicum.ewmclient.model.UpdateEventAdminRequest;

import java.io.IOException;

@JsonTest
public class UpdateEventAdminRequestTests {

    @Autowired
    private JacksonTester<UpdateEventAdminRequest> json;

    @Test
    void updateEventAdminRequest() throws IOException {
        LocationDto locationDto = LocationDto.builder()
                .id(1)
                .lon(11.11)
                .lat(22.22)
                .build();
        UpdateEventAdminRequest dto = UpdateEventAdminRequest.builder()
                .category(1)
                .eventDate("2010-10-10 10:10")
                .annotation("updateAnnotation")
                .description("updateDescription")
                .location(locationDto)
                .paid(false)
                .requestModeration(true)
                .participantLimit(10)
                .stateAction("PUBLISH_EVENT")
                .build();

        JsonContent<UpdateEventAdminRequest> result = json.write(dto);
        Assertions.assertEquals("{\"annotation\":\"updateAnnotation\",\"category\":1," +
                        "\"description\":\"updateDescription\",\"eventDate\":\"2010-10-10 10:10\"," +
                        "\"location\":{\"id\":1,\"lat\":22.22,\"lon\":11.11},\"paid\":false,\"participantLimit\":10," +
                        "\"requestModeration\":true,\"stateAction\":\"PUBLISH_EVENT\"}", result.getJson(),
                "Ошибка в обработке UpdateEventAdminRequest.");
    }

}