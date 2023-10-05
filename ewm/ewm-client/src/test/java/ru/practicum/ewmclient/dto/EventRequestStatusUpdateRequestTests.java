package ru.practicum.ewmclient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.request.EventRequestStatusUpdateRequest;

import java.io.IOException;
import java.util.List;

@JsonTest
class EventRequestStatusUpdateRequestTests {

    @Autowired
    private JacksonTester<EventRequestStatusUpdateRequest> json;

    @Test
    void eventRequestStatusUpdateRequest() throws IOException {
        EventRequestStatusUpdateRequest dto = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1, 2, 3))
                .status("CONFIRMED")
                .build();

        JsonContent<EventRequestStatusUpdateRequest> result = json.write(dto);
        Assertions.assertEquals("{\"requestIds\":[1,2,3],\"status\":\"CONFIRMED\"}", result.getJson(),
                "Ошибка в обработке EventRequestStatusUpdateRequest.");
    }

}