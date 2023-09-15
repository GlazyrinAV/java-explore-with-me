package ru.practicum.ewmservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmclient.model.ParticipationRequestDto;

import java.io.IOException;

@JsonTest
public class ParticipationRequestDtoTests {

    @Autowired
    private JacksonTester<ParticipationRequestDto> json;

    @Test
    void participationRequestDto() throws IOException {
        ParticipationRequestDto dto = ParticipationRequestDto.builder()
                .requester(1)
                .event(2)
                .status("PENDING")
                .id(1)
                .created("2011-11-11 11:11")
                .build();

        JsonContent<ParticipationRequestDto> result = json.write(dto);
        Assertions.assertEquals("{\"id\":1,\"event\":2,\"created\":\"2011-11-11 11:11\"," +
                        "\"requester\":1,\"status\":\"PENDING\"}", result.getJson(),
                "Ошибка в обработке ParticipationRequestDto.");
    }

}