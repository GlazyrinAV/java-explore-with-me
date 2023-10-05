package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.request.EventRequestStatusUpdateResult;
import ru.practicum.ewmclient.model.request.ParticipationRequestDto;

import java.io.IOException;
import java.util.List;

@JsonTest
class EventRequestStatusUpdateResultTests {

    @Autowired
    private JacksonTester<EventRequestStatusUpdateResult> json;

    @Test
    void eventRequestStatusUpdateResult() throws IOException {
        ParticipationRequestDto approved = ParticipationRequestDto.builder()
                .status("CONFIRMED")
                .event(1)
                .requester(1)
                .build();
        ParticipationRequestDto rejected = ParticipationRequestDto.builder()
                .status("REJECTED")
                .event(2)
                .requester(2)
                .build();
        EventRequestStatusUpdateResult dto = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(List.of(approved))
                .rejectedRequests(List.of(rejected))
                .build();

        JsonContent<EventRequestStatusUpdateResult> result = json.write(dto);
        Assertions.assertEquals("{\"confirmedRequests\":[{\"id\":0,\"event\":1,\"requester\":1," +
                        "\"status\":\"CONFIRMED\"}],\"rejectedRequests\":[{\"id\":0,\"event\":2,\"requester\":2," +
                        "\"status\":\"REJECTED\"}]}", result.getJson(),
                "Ошибка в обработке EventRequestStatusUpdateResult.");
    }

}